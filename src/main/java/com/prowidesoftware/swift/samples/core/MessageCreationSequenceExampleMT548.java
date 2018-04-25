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

import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field20C;
import com.prowidesoftware.swift.model.field.Field23G;
import com.prowidesoftware.swift.model.mt.mt5xx.MT548;

/**
 * Example of message creation using a specific MTnnn class, the Sequences API and
 * nested subsequences. 
 * A partial MT548 is created, including a sequence A with inner sequences A1 and A2. 
 * 
 * @author www@prowidesoftware.com
 *
 */
public class MessageCreationSequenceExampleMT548 {

	public static void main(String[] args) {
		MT548 mt = new MT548().append(MT548.SequenceA.newInstance(
				/* full content of sequence A here 
				 * We create a SwiftTagListBlock to append both field and subsequences */
				new SwiftTagListBlock()
					.append(Field20C.emptyTag())
					.append(Field23G.emptyTag())
					.append(MT548.SequenceA1.newInstance(
							// Sequence A1 contents here
							))
					.append(MT548.SequenceA2.newInstance(
							// Sequence A2 contents here
							))
				));
		/*
		 * Create and print out the SWIFT FIN message string
		 */
		System.out.println(mt.message());
	}
}
