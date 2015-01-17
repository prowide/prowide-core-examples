/*
 * Copyright (c) http://www.prowidesoftware.com/, 2014. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

/**
 * This example shows how to read a SWIFT MT message from a file, in the context where
 * the message type to parse is already known, in the example we use an MT 103.
 * 
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class ParseMT103FromFileExample {

	public static void main(String[] args) throws IOException {
		/*
		 * Read and parse the file content into a SWIFT message object
		 */
		MT103 mt = MT103.parse(new File("src/mt103.txt"));
		
		/*
		 * Print header information
		 */
		System.out.println("Sender: "+mt.getSender());
		System.out.println("Receiver: "+mt.getReceiver());

		/*
		 * Print details of a specific fields
		 */
		System.out.println("Reference: "+mt.getField20().getValue());

		Field32A f = mt.getField32A();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		System.out.println("Value Date: "+sdf.format(f.getDateAsCalendar().getTime()));
		System.out.println("Amount: "+f.getCurrency()+" "+f.getAmount());
	}
}
