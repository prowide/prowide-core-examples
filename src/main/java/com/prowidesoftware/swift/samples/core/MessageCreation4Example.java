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

import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt2xx.MT201;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Example of a simple message creation including a repetitive sequence with no boundary fields.
 * An MT201 is created and converted to the SWIFT FIN format.
 * 
 * Running this program produces the following SWIFT FIN message content:
<pre>
{1:F01FOOSEDR0AXXX0000000000}{2:I201FOORECV0XXXXN}{4:
:19:10000,
:30:190827
:20:REFERENCE1
:32B:USD3000,
:57A:ABCDUSXXXXX
:20:REFERENCE2
:32B:USD7000,
:57A:/1234567890
EFGHUSXXXXX
-}
</pre>
 */
public class MessageCreation4Example {

    /**
     * This example creates a new MT201 using MT and Field helper classes.
     */
    public static void main(String[] args) throws Exception {
		/*
		 * Create the MT class, it will be initialized as an outgoing message with normal priority
		 */
		final MT201 m = new MT201();
	
		/*
		 * Set sender and receiver BIC codes
		 */
		m.setSender("FOOSEDR0AXXX");
		m.setReceiver("FOORECV0XXXX");
	
		/*
		 * Start adding the message's fields in correct order
		 */
		m.addField(new Field19().setAmount(new BigDecimal("10000.00")));
		m.addField(new Field30("190827"));
	
		/*
		 * Add first instance of repetitive sequence B
		 * Since there are no boundary fields for the sequence we just add the fields in order
		 */
		m.addField(new Field20("REFERENCE1"));
		m.addField(new Field32B().setCurrency(Currency.getInstance("USD")).setAmount(new BigDecimal("3000.00")));
		m.addField(new Field57A().setBIC("ABCDUSXXXXX"));

		/*
		 * Add another instance of repetitive sequence B
		 */
		m.addField(new Field20("REFERENCE2"));
		m.addField(new Field32B().setCurrency(Currency.getInstance("USD")).setAmount(new BigDecimal("7000.00")));
		m.addField(new Field57A().setBIC("EFGHUSXXXXX").setAccount("1234567890"));

		/*
		 * As an alternative you could also create the sequences as SwiftTagListBlock and then use
		 * m.append(SwiftTagListBlock) to add them
		 */

		/*
		 * Create and print out the SWIFT FIN message string
		 */
		System.out.println(m.message());
    }
}
