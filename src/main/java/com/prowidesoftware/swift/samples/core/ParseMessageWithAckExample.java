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
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

import java.io.IOException;

/**
 * There is a frequent misunderstanding of the FIN messages format when the actual message is preceded by a system ACK, and the expected behavior of Prowide Core parser when reading such messages.
 * <p>
 * By default the parser will get the first FIN message found in the file (the ACK service message), leaving the rest of the text into the UnparsedTextList structure.
 * <p>
 * This is just fine when reading plain user to user messages, but when the message is preceded by a service message as in the example, the resulting parsed object may not be the expected one.
 * <p>
 * When dealing with this scenario it is the user responsibility to check whether the message is a service message or not, and proceed accordingly, depending on the particular use case and application needs.
 * <p>
 * If you are trying to match and process the ACK/NAK notifications you may be interested on the service message. However if this is the way you receive the messages from the SWIFT interface and you need the actual user message following the ACK, then you have to do something else.
 * <p>
 * The following example will parse the FIN content, check for the service id, and if it is a system message it will then gather the actual MT from the unparsed content:
 */
public class ParseMessageWithAckExample {

    public static void main(String[] args) throws IOException {
        final String fin = "{1:F21FOOLHKH0AXXX0304009999}{4:{177:1608140809}{451:0}}{1:F01FOOLHKH0AXXX0304009999}{2:O9401609160814FOOLHKH0AXXX03040027341608141609N}{4:\n" +
                ":20:USD940NO1\n" +
                ":21:123456/DEV\n" +
                ":25:USD234567\n" +
                ":28C:1/1\n" +
                ":60F:C160418USD672,\n" +
                ":61:160827C642,S1032\n" +
                ":86:ANDY\n" +
                ":61:160827D42,S1032\n" +
                ":86:BANK CHARGES\n" +
                ":62F:C160418USD1872,\n" +
                ":64:C160418USD1872,\n" +
                "-}{5:{CHK:0FEC1E4AEC53}{TNG:}}{S:{COP:S}}";

        SwiftMessage sm = SwiftMessage.parse(fin);
        if (sm.isServiceMessage()) {
            sm = SwiftMessage.parse(sm.getUnparsedTexts().getAsFINString());
        }
        //at this point the sm variable will contain the actual user to user message, regardless if it was preceded by and ACK.

        System.out.println("Message Type: " + sm.getType());
        if (sm.isType(940)) {
            /*
             * Specialize the message to its specific model representation
             */
            MT940 mt = new MT940(sm);

            /*
             * Print details of a specific field
             */
            System.out.println("Reference: " + mt.getField20().getValue());
        }
    }

}
