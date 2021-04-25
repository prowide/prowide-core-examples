/*
 * Copyright 2006-2021 Prowide
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.prowidesoftware.swift.samples.core;

import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field21;
import com.prowidesoftware.swift.model.field.Field25;
import com.prowidesoftware.swift.model.field.Field28C;
import com.prowidesoftware.swift.model.field.Field60F;
import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.field.Field62F;
import com.prowidesoftware.swift.model.field.Field86;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import com.prowidesoftware.swift.model.mt.mt9xx.MT942;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This example shows how to create an MT940 daily statement from a couple of interim MT942
 * statements. It can be use as general example of how to create a new message using data gather
 * from other existing messages.
 */
public class MessageCreationMT942ToMT940Example {

  public static void main(String[] args) {

    /*
     * First interim statement for account 123456789
     */
    MT942 interimStatement1 = MT942
        .parse("{1:F01AAAAAEADAXXX0000000000}{2:I942BBBBOMRXXXXXN}{4:\n" +
            ":20:REF1\n" +
            ":25:123456789\n" +
            ":28C:1/2\n" +
            ":34F:EUR0,\n" +
            ":13D:1910200900+0400\n" +
            ":61:191020C15000,NCHKAA8876//1234\n" +
            ":86:ABCDD128231\n" +
            ":61:191020D7000,NTRFMT101 260310//0326132225123478\n" +
            ":86:AIIJSIDJ\n" +
            ":90D:1EUR7000,\n" +
            ":90C:1EUR15000,\n" +
            "-}");

    /*
     * First interim statement for the same account
     */
    MT942 interimStatement2 = MT942
        .parse("{1:F01AAAAAEADAXXX0000000000}{2:I942BBBBOMRXXXXXN}{4:\n" +
            ":20:REF2\n" +
            ":25:123456789\n" +
            ":28C:2/2\n" +
            ":34F:EUR0,\n" +
            ":13D:1910201000+0400\n" +
            ":61:191020C2000,NCHKAA8877//1234\n" +
            ":86:ABCDD128231\n" +
            ":61:191020D3000,NTRFMTREF222//023434\n" +
            ":86:AIIJSIDJ\n" +
            ":90D:1EUR3000,\n" +
            ":90C:1EUR2000,\n" +
            "-}");

    List<MT942> interimStatements = new ArrayList<>();
    interimStatements.add(interimStatement1);
    interimStatements.add(interimStatement2);

    /*
     * Create the new message, with same sender and receiver as of any of the MT942
     */
    MT940 mt = new MT940(interimStatement1.getSender(), interimStatement1.getReceiver());

    /*
     * Add a new reference field
     */
    mt.append(new Field20("NEW_REFERENCE"));

    /*
     * Set the related reference to the 942 reference
     */
    mt.append(new Field21(interimStatement1.getField20().getValue()));

    /*
     * Set the account identification with the account identification of any of the 942
     */
    mt.append(new Field25(interimStatement1.getField25().getValue()));

    /*
     * Set the statement number to 1
     */
    mt.append(new Field28C("1/1"));

    /*
     * Set the opening balance (should be known in advance)
     */
    BigDecimal openingAmount = new BigDecimal("10000.00");
    Field60F field60F = new Field60F()
        .setDCMark("C")
        .setDate("191020")
        .setCurrency("EUR")
        .setAmount(openingAmount);
    mt.append(field60F);

    BigDecimal closingAmount = addStatementLines(mt, interimStatements, openingAmount);

    /*
     * Append the computed closing balance
     */
    Field62F field62F = new Field62F()
        .setDate("191020")
        .setCurrency("EUR")
        .setAmount(closingAmount);
    mt.append(field62F);

    System.out.println(mt.message());
  }

  /**
   * Iterates the MT942 statements copying the transaction lines into the MT940 and computing the
   * final balance
   */
  private static BigDecimal addStatementLines(MT940 mt, List<MT942> interimStatements,
      BigDecimal openingAmount) {
    BigDecimal balance = openingAmount;
    for (MT942 statement : interimStatements) {
      balance = addStatementLines(mt, statement, balance);
    }
    return balance;
  }

  /**
   * Copies the MT942 statement lines into the MT940 and computes the final balance
   */
  private static BigDecimal addStatementLines(MT940 mt, MT942 interimStatement,
      BigDecimal openingAmount) {
    BigDecimal balance = openingAmount;

    List<Field> fields = interimStatement.getFields();
    for (int i = 0; i < fields.size(); i++) {
      Field field = fields.get(i);

      if (Field61.NAME.equals(field.getName())) {
        Field61 field61 = (Field61) field;

        /*
         * Add the statement line to the MT940
         */
        mt.append(new Field61(field61.getValue()));

        /*
         * Look ahead for field 86
         */
        if (i + 1 < fields.size() && Field86.NAME.equals(fields.get(i + 1).getName())) {
          /*
           * Add the statement line information also to the MT940
           */
          mt.append(new Field86(fields.get(i + 1).getValue()));
          i++;
        }

        /*
         * Compute balance considering the Debit/Credit mark
         * C: Credit
         * D: Debit
         * RC: Reversal of Credit
         * RD: Reversal of Debit
         */
        if ("D".equals(field61.getDCMark()) || "RC".equals(field61.getDCMark())) {
          balance = balance.subtract((BigDecimal) field61.getAmountAsNumber());
        } else {
          balance = balance.add((BigDecimal) field61.getAmountAsNumber());
        }
      }
    }

    return balance;
  }

}
