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
import java.text.SimpleDateFormat;

import com.prowidesoftware.swift.io.RJEReader;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.utils.Lib;

/**
 * This example shows how to read a SWIFT MT message from an RJE file.
 * The example file used contains two MT103 messages.
 * <br />
 * The RJE reader is scheduled for release in version 7.8 
 * In the meantime it is available on source code, or upon request.
 * 
 * @author www.prowidesoftware.com
 * @since 7.8
 */
public class ReadRJEFileExample {

	public static void main(String[] args) throws IOException {
		/*
		 * Read and parse the file content into a SWIFT message object
		 * Parse from File could also be used here
		 */
		RJEReader reader = new RJEReader(Lib.readResource("mt103.rje", null));
		
		/*
		 * Iterate the reader
		 */
		while (reader.hasNext()) {
			/*
			 * Read the message. 
			 */
			//System.out.println(reader.next());
			AbstractMT msg = reader.nextMT();
			
			if (msg.isType(103)) {
				
				/*
				 * Specialize the message 
				 */
				MT103 mt = (MT103) msg;
				
				/*
				 * Print some content from message
				 * 
				 * Expected output:
				 * Sender: FOOTUS3NBXXX
				 * Receiver: BICFOOYYAXXX
				 * Reference: FDF0510141142100
				 * Value Date: 2005/10/14
				 * Amount: USD 1814,28
				 * --------------------------
				 * Sender: FOOBESMMAXXX
				 * Receiver: BICFOOYYAXXX
				 * Reference: INGDESMM
				 * Value Date: 2005/10/28
				 * Amount: EUR 111222,33
				 * --------------------------
				 */
				System.out.println("Sender: "+mt.getSender());
				System.out.println("Receiver: "+mt.getReceiver());
				System.out.println("Reference: "+mt.getField20().getValue());
				
				Field32A f = mt.getField32A();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				System.out.println("Value Date: "+sdf.format(f.getDateAsCalendar().getTime()));
				System.out.println("Amount: "+f.getCurrency()+" "+f.getAmount());
				System.out.println("--------------------------");
			}
		}
	}
}
