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

import com.prowidesoftware.swift.io.PPCWriter;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field23B;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

/**
 * Example to create some messages and serialize them into an RJE file.
 * <br />
 * The DOS-PPC writer is scheduled for release in version 7.8 
 * In the meantime it is available on source code, or upon request.
 * 
 * @author sebastian@prowidesoftware.com
 * @since 7.8
 */
public class WriteDOSPCCFileExample {

    /**
     * This example creates a new MT103 using MT and Field helper classes.
     */
    public static void main(String[] args) throws Exception {
		/*
		 * Create a MT103 message.
		 * 
		 * Notice the message is not complete, partially created in this example.
		 * Check the creation examples to see how to create messages.
		 */
		final MT103 m = new MT103();
		m.setSender("FOOSEDR0AXXX");
		m.setReceiver("FOORECV0XXXX");
		m.addField(new Field20("REFERENCE"));
		m.addField(new Field23B("CRED"));
	
		/*
		 * Create the writer
		 */
		PPCWriter writer = new PPCWriter("/tmp/test.dos");
		
		/*
		 * Write the message ito the file
		 */
		writer.write(m);
		
		/*
		 * Add message again.
		 * Notice RJE files support multiple messages. 
		 * We add the same message twice just for the purpose of the example.
		 */
		writer.write(m);

		/*
		 * Close the writer stream
		 */
		writer.close();
    }
}
