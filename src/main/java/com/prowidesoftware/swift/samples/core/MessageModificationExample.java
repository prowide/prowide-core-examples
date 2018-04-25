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
import java.util.Calendar;

import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.field.Field57A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.utils.Lib;

/**
 * This example shows how to modify specific content from a SWIFT MT message read from file.
 * 
 * Running this program will change current message content...
<pre>
:20:0061350113089908
:32A:061028EUR100000,
:57A:BICFOOYYXXX
</pre>
 * into this:
<pre>
:20:NEWREFERENCE
:32A:161104EUR100000,
:57A:/12345
</pre>
 *
 * <p>For more information of the underlying generic message model check: 
 * http://api.prowidesoftware.com/core/com/prowidesoftware/swift/model/SwiftMessage.html
 * </p>
 * 
 * <p>For detailed available API to retrieve and alter content in the text block of MT messages, check:
 * http://api.prowidesoftware.com/core/com/prowidesoftware/swift/model/SwiftTagListBlock.html
 * </p>
 * 
 * @author www.prowidesoftware.com
 * 
 */
public class MessageModificationExample {

	public static void main(String[] args) throws IOException {
		/*
		 * Read and parse the file content from resources into a SWIFT message object
		 * Parse from File could also be used here
		 */
		MT103 mt = MT103.parse(Lib.readResource("mt103.txt", null));
		
		/*
		 * Print current message content
		 */
		System.out.println("before\n:"+mt.message());
		
		/*
		 * Notice the MT103 and its getFieldNN API are a facade
		 * intended for parse/read, not for modification.
		 * 
		 * To change values, the underlying SwiftMessage object
		 * and its Tag objects must be use
		 */
		SwiftBlock4 b4 = mt.getSwiftMessage().getBlock4();
		
		/*
		 * Change the reference field by setting its complete value
		 */
		b4.getTagByName("20").setValue("NEWREFERENCE");
		
		/*
		 * Change field 57 with new content from new field
		 */
		Field57A field57A = new Field57A();
		field57A.setAccount("12345");
		field57A.setBIC("NEWAESMMXXX");
		b4.getTagByName("57A").setValue(field57A.getValue());
		
		/*
		 * Update just the value date component in field 32A
		 * First we get current field and just change the date, notice the read
		 * Field instance is a detached object, changing it does not modify
		 * the actual message.
		 * Then use the detached modified field to update the current Tag
		 * value in the underlying message
		 */
		Field32A field32A = mt.getField32A().setComponent1(Calendar.getInstance());
		b4.getTagByName("32A").setValue(field32A.getValue());

		System.out.println("after\n:"+mt.message());
	}
}
