/*
 * Copyright (c) http://www.prowidesoftware.com/, 2013. All rights reserved.
 */
package com.prowidesoftware.swift.samples.core;

import java.io.IOException;
import java.io.StringWriter;

import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.io.writer.XMLWriterVisitor;
import com.prowidesoftware.swift.model.SwiftMessage;

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
 * 
 * @author www.prowidesoftware.com
 */
public class MessageToJsonExample {

    public static void main(String[] args) throws IOException {
		/*
		 * This will be published in version 7.5 of CORE
		 */
		String fin = "{1:F01BICFOOYYAXXX8683497519}{2:O1031535051028ESPBESMMAXXX54237522470510281535N}{3:{113:ROMF}{108:0510280182794665}{119:STP}}{4:\n"
		        + ":20:0061350113089908\n" + ":13C:/RNCTIME/1534+0000\n" + ":23B:CRED\n" + ":23E:SDVA\n" + ":32A:061028EUR100000,\n" + ":33B:EUR100000,\n"
		        + ":50K:/12345678\n" + "AGENTES DE BOLSA FOO AGENCIA\n" + "AV XXXXX 123 BIS 9 PL\n" + "12345 BARCELONA\n" + ":52A:/2337\n" + "FOOAESMMXXX\n"
		        + ":53A:FOOAESMMXXX\n" + ":57A:BICFOOYYXXX\n" + ":59:/ES0123456789012345671234\n" + "FOO AGENTES DE BOLSA ASOC\n" + ":71A:OUR\n"
		        + ":72:/BNF/TRANSF. BCO. FOO\n" + "-}{5:{MAC:88B4F929}{CHK:22EF370A4073}}\n" + "\n";
		SwiftParser parser = new SwiftParser(fin);
		SwiftMessage mt = parser.message();
	
		System.out.println(mt.toJson());
    }
}
