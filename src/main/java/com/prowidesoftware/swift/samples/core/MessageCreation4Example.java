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

import com.prowidesoftware.swift.model.field.Field19;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field30;
import com.prowidesoftware.swift.model.field.Field32B;
import com.prowidesoftware.swift.model.field.Field57A;
import com.prowidesoftware.swift.model.mt.mt2xx.MT201;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * Example of a simple message creation including a repetitive sequence with no boundary fields. An
 * MT201 is created and converted to the SWIFT FIN format.
 * <p>
 * Running this program produces the following SWIFT FIN message content:
 * <pre>
 * {1:F01FOOSEDR0AXXX0000000000}{2:I201FOORECV0XXXXN}{4:
 * :19:10000,
 * :30:190827
 * :20:REFERENCE1
 * :32B:USD3000,
 * :57A:ABCDUSXXXXX
 * :20:REFERENCE2
 * :32B:USD7000,
 * :57A:/1234567890
 * EFGHUSXXXXX
 * -}
 * </pre>
 */
public class MessageCreation4Example {

  /**
   * This example creates a new MT201 using MT and Field helper classes.
   */
  public static void main(String[] args) {
    /*
     * Create the MT class, it will be initialized as an outgoing message with normal priority
     */
    final MT201 m = new MT201();

    /*
     * Set sender and receiver BIC codes
     */
    m.setSender("FOOSEDR0AXXX");
    m.setReceiver("FOORECV0XXXX");

    /*
     * Start adding the message's fields in correct order
     */
    m.addField(new Field19().setAmount(new BigDecimal("10000.00")));
    m.addField(new Field30("190827"));

    /*
     * Add first instance of repetitive sequence B
     * Since there are no boundary fields for the sequence we just add the fields in order
     */
    m.addField(new Field20("REFERENCE1"));
    m.addField(new Field32B().setCurrency(Currency.getInstance("USD"))
        .setAmount(new BigDecimal("3000.00")));
    m.addField(new Field57A().setBIC("ABCDUSXXXXX"));

    /*
     * Add another instance of repetitive sequence B
     */
    m.addField(new Field20("REFERENCE2"));
    m.addField(new Field32B().setCurrency(Currency.getInstance("USD"))
        .setAmount(new BigDecimal("7000.00")));
    m.addField(new Field57A().setBIC("EFGHUSXXXXX").setAccount("1234567890"));

    /*
     * As an alternative you could also create the sequences as SwiftTagListBlock and then use
     * m.append(SwiftTagListBlock) to add them
     */

    /*
     * Create and print out the SWIFT FIN message string
     */
    System.out.println(m.message());
  }
}
