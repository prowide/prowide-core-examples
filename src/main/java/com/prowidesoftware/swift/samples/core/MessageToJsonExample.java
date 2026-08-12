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

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;

import java.io.IOException;

/**
 * Example of message conversion to JSON representation.
 *
 * <p>
 * running this sample will generate something like:
 * </p>
 *
 * <pre>
 * { "version" : 1,
 *  "timestamp" : '2014-08-08 07:11 -0300',
 *  "data" : {
 * "block1" :
 * {
 * "applicationId" : "F",
 * "serviceId" : "01",
 * "logicalTerminal" : "BICFOOYYAXXX",
 * "sessionNumber" : "8683",
 * "sequenceNumber" : "497519"
 * } ,
 * "block2" :
 * {
 *  "messageType" : "103",
 *  "senderInputTime" : "1535",
 *  "MIRDate" : "051028",
 *  "MIRLogicalTerminal" : "ESPBESMMAXXX",
 *  "MIRSessionNumber" : "5423",
 *  "MIRSequenceNumber" : "752247",
 *  "receiverOutputDate" : "051028",
 *  "receiverOutputTime" : "1535",
 *  "messagePriority" : "N"
 * } ,
 * "block3" :
 * [
 * { "113" : "ROMF" },
 * { "108" : "0510280182794665" },
 * { "119" : "STP" }
 * ]
 * ,"block4" :
 * [
 * { "20" : "0061350113089908" },
 * { "13C" : "/RNCTIME/1534+0000" },
 * { "23B" : "CRED" },
 * { "23E" : "SDVA" },
 * { "32A" : "061028EUR100000," },
 * { "33B" : "EUR100000," },
 * { "50K" : "/12345678\nAGENTES DE BOLSA FOO AGENCIA\nAV XXXXX 123 BIS 9 PL\n12345 BARCELONA" },
 * { "52A" : "/2337\nFOOAESMMXXX" },
 * { "53A" : "FOOAESMMXXX" },
 * { "57A" : "BICFOOYYXXX" },
 * { "59" : "/ES0123456789012345671234\nFOO AGENTES DE BOLSA ASOC" },
 * { "71A" : "OUR" },
 * { "72" : "/BNF/TRANSF. BCO. FOO" }
 * ]
 * ,"block5" :
 * [
 * { "MAC" : "88B4F929" },
 * { "CHK" : "22EF370A4073" }
 * ]
 * }
 * }
 * </pre>
 */
public class MessageToJsonExample {

    public static void main(String[] args) throws IOException {
        /*
         * This will be published in version 7.5 of CORE
         */
        String fin = "{1:F01BICFOOYYAXXX8683497519}{2:O1031535051028ESPBESMMAXXX54237522470510281535N}{3:{113:ROMF}{108:0510280182794665}{119:STP}}{4:\n" +
                ":20:0061350113089908\n" +
                ":13C:/RNCTIME/1534+0000\n" +
                ":23B:CRED\n" +
                ":23E:SDVA\n" +
                ":32A:061028EUR100000,\n" +
                ":33B:EUR100000,\n" +
                ":50K:/12345678\n" +
                "AGENTES DE BOLSA FOO AGENCIA\n" +
                "AV XXXXX 123 BIS 9 PL\n" +
                "12345 BARCELONA\n" +
                ":52A:/2337\n" +
                "FOOAESMMXXX\n" +
                ":53A:FOOAESMMXXX\n" +
                ":57A:BICFOOYYXXX\n" +
                ":59:/ES0123456789012345671234\n" +
                "FOO AGENTES DE BOLSA ASOC\n" +
                ":71A:OUR\n" +
                ":72:/BNF/TRANSF. BCO. FOO\n" +
                "-}{5:{MAC:88B4F929}{CHK:22EF370A4073}}";
        SwiftParser parser = new SwiftParser(fin);
        SwiftMessage mt = parser.message();

        System.out.println(mt.toJson());
    }

}
