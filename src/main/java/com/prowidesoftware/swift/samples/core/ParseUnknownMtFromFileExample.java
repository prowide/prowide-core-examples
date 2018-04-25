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

import java.io.File;
import java.io.IOException;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103_STP;
import com.prowidesoftware.swift.utils.Lib;

/**
 * This example shows how to read a SWIFT MT message from a file, in the context where
 * the message type to parse is unknown.
 * 
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class ParseUnknownMtFromFileExample {

	public static void main(String[] args) throws IOException {
		/*
		 * Read and parse the file content into a SWIFT message object
		 * Parse from File could also be used here
		 */
		AbstractMT msg = AbstractMT.parse(Lib.readResource("mt103.txt", null));
		
		/*
		 * Print header information
		 */
		System.out.println("Sender: "+msg.getSender());
		System.out.println("Receiver: "+msg.getReceiver());
		System.out.println("MT: "+msg.getMessageType());

		if (msg.isType(103) && msg.getSwiftMessage().isSTP()) {
			/*
			 * Specialize the message to its specific model representation
			 */
			MT103_STP mt = (MT103_STP) msg;
			
			/*
			 * Print details of a specific field
			 */
			System.out.println("Reference: "+mt.getField20().getValue());
		}
	}
}
