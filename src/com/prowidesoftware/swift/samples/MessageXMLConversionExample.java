/*
 * Copyright (c) http://www.prowidesoftware.com/, 2013. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.io.StringWriter;

import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.writer.XMLWriterVisitor;

/**
 * Example of message conversion to XML representation.
 * Notice this is not SWIFT MX format, but the internal/proprietary XML representation defined at WIFE.
 * 
 * <p>running this sample will generate something like:</p>
 * <pre>
&lt;message&gt;
	&lt;block1&gt;
		&lt;applicationId&gt;F&lt;/applicationId&gt;
		&lt;serviceId&gt;01&lt;/serviceId&gt;
		&lt;logicalTerminal&gt;BICFOOYYAXXX&lt;/logicalTerminal&gt;
		&lt;sessionNumber&gt;8683&lt;/sessionNumber&gt;
		&lt;sequenceNumber&gt;497519&lt;/sequenceNumber&gt;
	&lt;/block1&gt;
	&lt;block2 type="output"&gt;
		&lt;messageType&gt;103&lt;/messageType&gt;
		&lt;senderInputTime&gt;1535&lt;/senderInputTime&gt;
		&lt;MIRDate&gt;051028&lt;/MIRDate&gt;
		&lt;MIRLogicalTerminal&gt;ESPBESMMAXXX&lt;/MIRLogicalTerminal&gt;
		&lt;MIRSessionNumber&gt;5423&lt;/MIRSessionNumber&gt;
		&lt;MIRSequenceNumber&gt;752247&lt;/MIRSequenceNumber&gt;
		&lt;receiverOutputDate&gt;051028&lt;/receiverOutputDate&gt;
		&lt;receiverOutputTime&gt;1535&lt;/receiverOutputTime&gt;
		&lt;messagePriority&gt;N&lt;/messagePriority&gt;
	&lt;/block2&gt;
	&lt;block3&gt;
		&lt;tag&gt;
			&lt;name&gt;113&lt;/name&gt;
			&lt;value&gt;ROMF&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;108&lt;/name&gt;
			&lt;value&gt;0510280182794665&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;119&lt;/name&gt;
			&lt;value&gt;STP&lt;/value&gt;
		&lt;/tag&gt;
	&lt;/block3&gt;
	&lt;block4&gt;
		&lt;tag&gt;
			&lt;name&gt;20&lt;/name&gt;
			&lt;value&gt;0061350113089908&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;13C&lt;/name&gt;
			&lt;value&gt;/RNCTIME/1534+0000&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;23B&lt;/name&gt;
			&lt;value&gt;CRED&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;23E&lt;/name&gt;
			&lt;value&gt;SDVA&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;32A&lt;/name&gt;
			&lt;value&gt;061028EUR100000,&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;33B&lt;/name&gt;
			&lt;value&gt;EUR100000,&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;50K&lt;/name&gt;
			&lt;value&gt;/12345678
	AGENTES DE BOLSA FOO AGENCIA
	AV XXXXX 123 BIS 9 PL
	12345 BARCELONA&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;52A&lt;/name&gt;
			&lt;value&gt;/2337
	FOOAESMMXXX&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;53A&lt;/name&gt;
			&lt;value&gt;FOOAESMMXXX&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;57A&lt;/name&gt;
			&lt;value&gt;BICFOOYYXXX&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;59&lt;/name&gt;
			&lt;value&gt;/ES0123456789012345671234
	FOO AGENTES DE BOLSA ASOC&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;71A&lt;/name&gt;
			&lt;value&gt;OUR&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;72&lt;/name&gt;
			&lt;value&gt;/BNF/TRANSF. BCO. FOO&lt;/value&gt;
		&lt;/tag&gt;
	&lt;/block4&gt;
	&lt;block5&gt;
		&lt;tag&gt;
			&lt;name&gt;MAC&lt;/name&gt;
			&lt;value&gt;88B4F929&lt;/value&gt;
		&lt;/tag&gt;
		&lt;tag&gt;
			&lt;name&gt;CHK&lt;/name&gt;
			&lt;value&gt;22EF370A4073&lt;/value&gt;
		&lt;/tag&gt;
	&lt;/block5&gt;
&lt;/message&gt;
</pre> 
 * @author www.prowidesoftware.com
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
				"\n" ;
		String xml = srv.getXml(fin);
		System.out.println(xml);
		
		// Convert the message as 
		
		// TODO addd useFIeld option when published 
	}
}
