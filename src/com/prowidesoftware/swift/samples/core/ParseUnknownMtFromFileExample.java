/*
 * Copyright (c) http://www.prowidesoftware.com/, 2014. All rights reserved.
 */
package com.prowidesoftware.swift.samples.core;

import java.io.File;
import java.io.IOException;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

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
		 */
		AbstractMT msg = AbstractMT.parse(new File("src/mt103.txt"));
		
		/*
		 * Print header information
		 */
		System.out.println("Sender: "+msg.getSender());
		System.out.println("Receiver: "+msg.getReceiver());
		System.out.println("MT: "+msg.getMessageType());

		if (msg.isType(103)) {
			/*
			 * Specialize the message to its specific model representation
			 */
			MT103 mt = (MT103) msg;
			
			/*
			 * Print details of a specific field
			 */
			System.out.println("Reference: "+mt.getField20().getValue());
		}
	}
}
