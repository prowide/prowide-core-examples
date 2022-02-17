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

import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt2xx.MT201;
import com.prowidesoftware.swift.model.mt.mt7xx.MT760;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Example of how to create an MT798 envelope message with the submessage type created from another existing MT
 * <p>
 * Running this program produces the following SWIFT FIN message content:
 * <pre>
 * {1:F01AAAADEM0AXXX0000000000}{2:I798BBBBITRRXMCEN}{4:
 * :20:1234
 * :12:760
 * :77E:
 * :27A:2/2
 * :21A:2201091711320000
 * :15A:
 * :27:1/1
 * :22A:ISSU
 * :15B:
 * :20:Bla Blah
 * :30:250109
 * :22D:DGAR
 * :40C:ISPR
 * :23B:FIXD
 * :31E:250109
 * :35G:If things happen
 * :50:Mr. App
 * This Way
 * Our City
 * :51:Mr. Obligor
 * His stay
 * :52D:Mr. Issue
 * :59:Mr. Bene
 * In Road
 * :56A:ANLAITRRMFE
 * :32B:USD23456789,
 * :77U:Terms and conditions
 * have been defined
 * :45L:Some details
 * about the underlying tx
 * :24E:MAIL
 * :24G:OTHR
 * Foobar
 * -}
 * </pre>
 */
public class MessageCreationMT798Example {

  /**
   * This example creates an MT798 envelope with an embedded MT760 message.
   */
  public static void main(String[] args) {
    /*
     * Source content of an MT760 to use as sub-message
     */
    String fin = "{1:F01AAAADEM0AXXX0000000000}{2:I760BBBBITRRXMCEN2020}{4:\n" +
            ":27A:2/2\n" +
            ":21A:2201091711320000\n" +
            ":15A:\n" +
            ":27:1/1\n" +
            ":22A:ISSU\n" +
            ":15B:\n" +
            ":20:Bla Blah\n" +
            ":30:250109\n" +
            ":22D:DGAR\n" +
            ":40C:ISPR\n" +
            ":23B:FIXD\n" +
            ":31E:250109\n" +
            ":35G:If things happen\n" +
            ":50:Mr. App\n" +
            "This Way\n" +
            "Our City\n" +
            ":51:Mr. Obligor\n" +
            "His stay\n" +
            ":52D:Mr. Issue\n" +
            ":59:Mr. Bene\n" +
            "In Road\n" +
            ":56A:ANLAITRRMFE\n" +
            ":32B:USD23456789,\n" +
            ":77U:Terms and conditions\n" +
            "have been defined\n" +
            ":45L:Some details\n" +
            "about the underlying tx\n" +
            ":24E:MAIL\n" +
            ":24G:OTHR\n" +
            "Foobar\n" +
            "-}";
    MT760 subMessage = MT760.parse(fin);

    /*
     * Create a new MT798 envelop message, copying the seder, receiver and message type from the MT760
     */
    MT798 mt798 = new MT798(subMessage.getSender(), subMessage.getReceiver());
    mt798
            .append(Field20.tag("1234"))
            .append(Field12.tag(subMessage.getMessageType()))
            .append(Field77E.emptyTag());

    /*
     * After field 77E copy all fields from the sub-message MT760
     */
    subMessage.getSwiftMessage().getBlock4().getTags().forEach(t -> mt798.append(t));

    /*
     * Print the content of the created MT798
     */
    System.out.println(mt798.message());
  }

}
