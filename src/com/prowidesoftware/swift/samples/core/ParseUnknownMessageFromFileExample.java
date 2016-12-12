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
import java.io.FileInputStream;
import java.io.IOException;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

/**
 * This example shows how to read a SWIFT MT message from a file, in the context where
 * the message type to parse is unknown and also it can be a system message.
 * This example uses the generic parser instead of the AbstractMT class.
 * 
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class ParseUnknownMessageFromFileExample {

	public static void main(String[] args) throws IOException {
		/*
		 * Read the file and create an instance of the generic parser for it
		 */
		File file = new File("src/mt103.txt");
		SwiftParser parser = new SwiftParser(new FileInputStream(file));		
		SwiftMessage msg = parser.message();

		if (msg.isSystemMessage()) {
			/*
			 * deal with system message
			 */
			msg.getBlock4().getTagByName("TAGNAME");
		} else {
			/*
			 * specialize message as necessary depending on message type
			 */
			if (msg.isType(103)) {
				/*
				 * Specialize the message to its specific model representation
				 */
				MT103 mt = new MT103(msg);
				
				/*
				 * Print details of a specific field
				 */
				System.out.println("Reference: "+mt.getField20().getValue());
			}
		}
	}
}
