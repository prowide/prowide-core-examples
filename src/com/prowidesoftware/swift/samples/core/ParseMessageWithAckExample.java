/*******************************************************************************
 * Copyright (c) 2016 Prowide Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as 
 *     published by the Free Software Foundation, either version 3 of the 
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *     
 *     Check the LGPL at <http://www.gnu.org/licenses/> for more details.
 *******************************************************************************/
package com.prowidesoftware.swift.samples.core;

import java.io.IOException;

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

/**
 * There is a frequent misunderstanding of the FIN messages format when the actual message is preceded by a system ACK, and the expected behavior of Prowide Core parser when reading such messages.
 * 
 * By default the parser will get the first FIN message found in the file (the ACK service message), leaving the rest of the text into the UnparsedTextList structure.
 * 
 * This is just fine when reading plain user to user messages, but when the message is preceded by a service message as in the example, the resulting parsed object may not be the expected one.
 * 
 * When dealing with this scenario it is the user responsibility to check whether the message is a service message or not, and proceed accordingly, depending on the particular use case and application needs.
 * 
 * If you are trying to match and process the ACK/NAK notifications you may be interested on the service message. However if this is the way you receive the messages from the SWIFT interface and you need the actual user message following the ACK, then you have to do something else.
 * 
 * The following example will parse the FIN content, check for the service id, and if it is a system message it will then gather the actual MT from the unparsed content:
 * 
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class ParseMessageWithAckExample {

	public static void main(String[] args) throws IOException {
		final String fin = "{1:F21FOOLHKH0AXXX0304009999}{4:{177:1608140809}{451:0}}{1:F01FOOLHKH0AXXX0304009999}{2:O9401609160814FOOLHKH0AXXX03040027341608141609N}{4:\n"+
			":20:USD940NO1\n"+
			":21:123456/DEV\n"+
			":25:USD234567\n"+
			":28C:1/1\n"+
			":60F:C160418USD672,\n"+
			":61:160827C642,S1032\n"+
			":86:ANDY\n"+
			":61:160827D42,S1032\n"+
			":86:BANK CHARGES\n"+
			":62F:C160418USD1872,\n"+
			":64:C160418USD1872,\n"+
			"-}{5:{CHK:0FEC1E4AEC53}{TNG:}}{S:{COP:S}}";
		
		SwiftMessage sm = SwiftMessage.parse(fin);
		if (sm.isServiceMessage()) {
			sm = SwiftMessage.parse(sm.getUnparsedTexts().getAsFINString());
		}
		//at this point the sm variable will contain the actual user to user message, regardless if it was preceded by and ACK.
		
		System.out.println("Message Type: "+ sm.getType());
		if (sm.isType(940)) {
			/*
			 * Specialize the message to its specific model representation
			 */
			MT940 mt = new MT940(sm);
			
			/*
			 * Print details of a specific field
			 */
			System.out.println("Reference: "+mt.getField20().getValue());
		}
	}
		
}
