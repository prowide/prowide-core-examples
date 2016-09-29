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

import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field23B;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.field.Field50A;
import com.prowidesoftware.swift.model.field.Field59;
import com.prowidesoftware.swift.model.field.Field71A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

/**
 * Example of a simple message creation using a specific MTnnn class. 
 * An MT103 is created and converted to the SWIFT FIN format.
 * 
 * Running this program produces the following SWIFT FIN message content:
<pre>
{1:F01FOOSEDR0AXXX0000000000}{2:I103FOORECV0XXXXN}{4:
:20:REFERENCE
:23B:CRED
:32A:141031EUR1234567,89
:50A:/12345678901234567890
FOOBANKXXXXX
:59:/12345678901234567890
JOE DOE
:71A:OUR
-}
</pre>
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class MessageCreation1Example {

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
		 * Set sender and receiver BIC codes
		 */
		m.setSender("FOOSEDR0AXXX");
		m.setReceiver("FOORECV0XXXX");
	
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
		 * Create and print out the SWIFT FIN message string
		 */
		System.out.println(m.message());
    }
}
