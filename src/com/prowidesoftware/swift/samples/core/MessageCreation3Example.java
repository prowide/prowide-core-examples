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

import java.util.Calendar;

import com.prowidesoftware.swift.model.SwiftBlock1;
import com.prowidesoftware.swift.model.SwiftBlock2Input;
import com.prowidesoftware.swift.model.SwiftBlock3;
import com.prowidesoftware.swift.model.SwiftBlock5;
import com.prowidesoftware.swift.model.SwiftBlockUser;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field119;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field23B;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.field.Field50A;
import com.prowidesoftware.swift.model.field.Field59;
import com.prowidesoftware.swift.model.field.Field71A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

/**
 * Example of message creation including explicit content for blocks 3 and S. 
 * An MT103 is created and converted to the SWIFT FIN format.
 * 
 * Running this program produces the following SWIFT FIN message content:
<pre>
{1:F01BICFOOYYAXXX1234123456}{2:I103BICFOARXXXXXN1}{3:{119:STP}}{4:
:20:REFERENCE
:23B:CRED
:32A:150116EUR1234567,89
:50A:/12345678901234567890
FOOBANKXXXXX
:59:/12345678901234567890
JOE DOE
:71A:OUR
-}{5:{MAC:00000000}{PDE:}}{S:{SAC:}{COP:P}}
</pre>
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class MessageCreation3Example {

    /**
     * This example creates a new MT103 using MT and Field helper classes.
     */
    public static void main(String[] args) throws Exception {
		/*
		 * Create the MT class, it will be initialized as an outgoing message
		 * with normal priority
		 */
		final MT103 m = new MT103();
	
		/*
		 * Create and set a specific header block 1 
		 */
		SwiftBlock1 b1 = new SwiftBlock1();
		b1.setApplicationId("F");
		b1.setServiceId("01");
		b1.setLogicalTerminal("BICFOOYYAXXX");
		b1.setSessionNumber("1234");
		b1.setSequenceNumber("123456");
		m.getSwiftMessage().setBlock1(b1);
		
		/*
		 * Create and set a specific header block 2
		 * Notice there are two block 2 headers (for input and output messages)
		 */
		SwiftBlock2Input b2 = new SwiftBlock2Input();
		b2.setMessageType("103");
		b2.setReceiverAddress("BICFOARXXXXX");
		b2.setDeliveryMonitoring("1");
		m.getSwiftMessage().setBlock2(b2);

		/*
		 * Add the optional user header block
		 */
		SwiftBlock3 block3 = new SwiftBlock3();
		block3.append(new Field119("STP"));
		m.getSwiftMessage().addBlock(block3);

		
		/*
		 * Start adding the message's fields in correct order
		 */
		m.addField(new Field20("REFERENCE"));
		m.addField(new Field23B("CRED"));
	
		/*
		 * Add a field using comprehensive setters API
		 */
		Field32A f32A = new Field32A()
			.setDate(Calendar.getInstance())
			.setCurrency("EUR")
			.setAmount("1234567,89");
		m.addField(f32A);
	
		/*
		 * Add the orderer field
		 */
		Field50A f50A = new Field50A()
			.setAccount("12345678901234567890")
			.setBIC("FOOBANKXXXXX");
		m.addField(f50A);
	
		/*
		 * Add the beneficiary field
		 */
		Field59 f59 = new Field59()
			.setAccount("12345678901234567890")
			.setNameAndAddress("JOE DOE");
		m.addField(f59);
	
		/*
		 * Add the commission indication
		 */
		m.addField(new Field71A("OUR"));
		

		/*
		 * Add the trailer block (in normal situations this is automatically created by the FIN interface, not by the user/applications)
		 */
		SwiftBlock5 block5 = new SwiftBlock5();
		block5.append(new Tag("MAC", "00000000"));
		block5.append(new Tag("PDE", ""));
		m.getSwiftMessage().addBlock(block5);
	
		/*
		 * Add an optional user block
		 */
		SwiftBlockUser blockUser = new SwiftBlockUser("S");
		blockUser.append(new Tag("SAC", ""));
		blockUser.append(new Tag("COP", "P"));
		m.getSwiftMessage().addBlock(blockUser);
	
		/*
		 * Create and print out the SWIFT FIN message string
		 */
		System.out.println(m.message());
    }
}
