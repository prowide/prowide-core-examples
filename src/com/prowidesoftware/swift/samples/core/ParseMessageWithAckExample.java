/*
 * Copyright (c) http://www.prowidesoftware.com/, 2014. All rights reserved.
 */
package com.prowidesoftware.swift.samples.core;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

/**
 * There is a frequent misunderstanding of the FIN messages format when the actual message
 * is preceded by a system ACK, and the expected behavior of Prowide Core parser when reading
 * such messages.
 * <br />
 * By default the parser will get the first FIN message found in the file, leaving the
 * rest into the UnparsedTextList structure.
 * <br />
 * This is just fine when reading plain FIN messages, but when the message is preceded by
 * an acknowledge (system message) for example, it is the user responsibility to check the
 * type of message read and proceed accordingly, depending on the particular use case and
 * application needs.
 * <br />
 * System messages are identified by service id "21" in the header block. For more
 * information check <a href="http://www.prowidesoftware.com/about-SWIFT-MT.jsp">about SWIFT MT</a>
 * <br />
 * The following example will parse the FIN content, check for the service id, and if it is a 
 * system message it will then gather the actual MT from the unparsed content.
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
		
		final SwiftParser parser = new SwiftParser();
		SwiftMessage sm = parser.parse(fin);
		if (StringUtils.equals(sm.getBlock1().getServiceId(), "21") && sm.getUnparsedTextsSize() > 0) {
			sm = sm.getUnparsedTexts().getTextAsMessage(0);
		}
		
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
