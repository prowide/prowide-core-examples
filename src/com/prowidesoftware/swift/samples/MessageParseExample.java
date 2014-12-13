/*
 * Copyright (c) http://www.prowidesoftware.com/, 2013. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

/**
 * Example of SWIFT messages parsing and content retrieving using MT and Field classes.
 * The SWIFT FIN text is read from a resource bundle but could be seamlessly read from the file system.
 * 
 * @author www.prowidesoftware.com
 */
public class MessageParseExample {

	public static void main(String[] args) throws IOException {
		/*
		 * Load the message text from a resource bundle
		 */
		String swiftFilename = Resources.getString("msg.1");
		/*
		 *  Create an instance of the SWIFT parser
		 */
		SwiftParser parser = new SwiftParser();
		/*
		 * Connect the file we want to parse with the parser
		 */
		parser.setReader(new FileReader(swiftFilename));
		/*
		 * Actually parse the file and create a java object model from the message
		 */
		SwiftMessage msg = parser.message();
		/*
		 * msg contains java object from parsed message.
		 * Printout several parts of the message's content.
		 */
		MT103 mt = new MT103(msg);
		
		System.out.println("Sender: "+mt.getSender());
		System.out.println("Receiver: "+mt.getReceiver());
		System.out.println("Reference: "+mt.getField20().getValue());
		Field32A f = mt.getField32A();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		System.out.println("Value Date: "+sdf.format(f.getDateAsCalendar().getTime()));
		System.out.println("Amount: "+f.getCurrency()+" "+f.getAmount());
	}
}
