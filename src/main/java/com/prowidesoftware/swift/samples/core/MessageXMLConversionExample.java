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

import com.prowidesoftware.swift.io.ConversionService;

/**
 * Example of message conversion to XML representation.
 * Notice this is not SWIFT MX format, but the internal/proprietary XML representation defined at WIFE.
 *
 * <p>running this sample will generate something like:</p>
 * <pre>
 * <message>
 * <block1>
 * <applicationId>F</applicationId>
 * <serviceId>01</serviceId>
 * <logicalTerminal>BICFOOYYAXXX</logicalTerminal>
 * <sessionNumber>8683</sessionNumber>
 * <sequenceNumber>497519</sequenceNumber>
 * </block1>
 * <block2 type="output">
 * <messageType>103</messageType>
 * <senderInputTime>1535</senderInputTime>
 * <MIRDate>051028</MIRDate>
 * <MIRLogicalTerminal>ESPBESMMAXXX</MIRLogicalTerminal>
 * <MIRSessionNumber>5423</MIRSessionNumber>
 * <MIRSequenceNumber>752247</MIRSequenceNumber>
 * <receiverOutputDate>051028</receiverOutputDate>
 * <receiverOutputTime>1535</receiverOutputTime>
 * <messagePriority>N</messagePriority>
 * </block2>
 * <block3>
 * <tag>
 * <name>113</name>
 * <value>ROMF</value>
 * </tag>
 * <tag>
 * <name>108</name>
 * <value>0510280182794665</value>
 * </tag>
 * <tag>
 * <name>119</name>
 * <value>STP</value>
 * </tag>
 * </block3>
 * <block4>
 * <tag>
 * <name>20</name>
 * <value>0061350113089908</value>
 * </tag>
 * <tag>
 * <name>13C</name>
 * <value>/RNCTIME/1534+0000</value>
 * </tag>
 * <tag>
 * <name>23B</name>
 * <value>CRED</value>
 * </tag>
 * <tag>
 * <name>23E</name>
 * <value>SDVA</value>
 * </tag>
 * <tag>
 * <name>32A</name>
 * <value>061028EUR100000,</value>
 * </tag>
 * <tag>
 * <name>33B</name>
 * <value>EUR100000,</value>
 * </tag>
 * <tag>
 * <name>50K</name>
 * <value>/12345678
 * AGENTES DE BOLSA FOO AGENCIA
 * AV XXXXX 123 BIS 9 PL
 * 12345 BARCELONA</value>
 * </tag>
 * <tag>
 * <name>52A</name>
 * <value>/2337
 * FOOAESMMXXX</value>
 * </tag>
 * <tag>
 * <name>53A</name>
 * <value>FOOAESMMXXX</value>
 * </tag>
 * <tag>
 * <name>57A</name>
 * <value>BICFOOYYXXX</value>
 * </tag>
 * <tag>
 * <name>59</name>
 * <value>/ES0123456789012345671234
 * FOO AGENTES DE BOLSA ASOC</value>
 * </tag>
 * <tag>
 * <name>71A</name>
 * <value>OUR</value>
 * </tag>
 * <tag>
 * <name>72</name>
 * <value>/BNF/TRANSF. BCO. FOO</value>
 * </tag>
 * </block4>
 * <block5>
 * <tag>
 * <name>MAC</name>
 * <value>88B4F929</value>
 * </tag>
 * <tag>
 * <name>CHK</name>
 * <value>22EF370A4073</value>
 * </tag>
 * </block5>
 * </message>
 * </pre>
 */
public class MessageXMLConversionExample {

    public static void main(String[] args) {
        ConversionService srv = new ConversionService();
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
                "-}{5:{MAC:88B4F929}{CHK:22EF370A4073}}\n" +
                "\n";
        String xml = srv.getXml(fin);
        System.out.println(xml);
    }
}
