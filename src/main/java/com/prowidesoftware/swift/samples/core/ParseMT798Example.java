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

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.mt.mt7xx.MT760;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

/**
 * This example shows how to read a SWIFT MT798 message envelop and its internal sub-message.
 * <p>
 * Running this program produces the following output:
 * <pre>
 * {1:F01AAAADEM0AXXX0000000000}{2:I760BBBBITRRXMCEN2020}{4:
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
public class ParseMT798Example {

    public static void main(String[] args) {
        /*
         * Parse the envelop MT798
         */
        String fin = "{1:F01AAAADEM0AXXX0000000000}{2:I798BBBBITRRXMCEN2020}{4:\n" +
                ":20:12345\n" +
                ":12:760\n" +
                ":77E:\n" +
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
        MT798 mt798 = MT798.parse(fin);

        /*
         * Get the sub-message (content after field 77E) as another model object
         */
        SwiftMessage subMessage = mt798.getSubMessage();

        /*
         * Convert and cast to MT760
         * (the type of the inner message is indicated in field 12 of the MT798)
         */
        MT760 mt = (MT760) subMessage.toMT();

        /*
         * Print the sub-message
         */
        System.out.println(mt.message());
    }

}
