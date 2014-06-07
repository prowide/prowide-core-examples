/*
 * Copyright (c) http://www.prowidesoftware.com/, 2013. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import com.prowidesoftware.swift.io.ConversionService;

/**
 * Example of message conversion to XML representation.
 * Notice this is not SWIFT MX format, but the internal/proprietary XML representation defined at WIFE.
 * 
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
	}
}
