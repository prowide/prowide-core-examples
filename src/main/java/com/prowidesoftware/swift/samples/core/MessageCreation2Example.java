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

import java.util.Calendar;

import com.prowidesoftware.swift.io.writer.FINWriterVisitor;
import com.prowidesoftware.swift.model.field.Field19A;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field20C;
import com.prowidesoftware.swift.model.field.Field22F;
import com.prowidesoftware.swift.model.field.Field23B;
import com.prowidesoftware.swift.model.field.Field23G;
import com.prowidesoftware.swift.model.field.Field35B;
import com.prowidesoftware.swift.model.field.Field36B;
import com.prowidesoftware.swift.model.field.Field70E;
import com.prowidesoftware.swift.model.field.Field90B;
import com.prowidesoftware.swift.model.field.Field95P;
import com.prowidesoftware.swift.model.field.Field95R;
import com.prowidesoftware.swift.model.field.Field97A;
import com.prowidesoftware.swift.model.field.Field98A;
import com.prowidesoftware.swift.model.mt.mt5xx.MT542;
import com.prowidesoftware.swift.model.mt.mt5xx.MT542.SequenceA;

/**
 * Example of message creation using a specific MTnnn class and the Sequences API. 
 * An MT542 is created and converted to the SWIFT FIN format.
 * 
 * Running this program produces the following SWIFT FIN message content:
 * 
 * <pre>
{1:F01FOOSEDR0AXXX0000000000}{2:I542FOORECV0XXXXN}{4:
:16R:GENL
:20C::SEME//2005071800000923
:23G:NEWM
:98A::PREP//20141031
:16S:GENL
:16R:TRADDET
:98A::TRAD//20050714
:98A::SETT//20050719
:90B::DEAL//ACTU/EUR21,49
:35B:ISIN FR1234567890
AXA UAP
:70E::SPRO//4042
:16S:TRADDET
:16R:FIAC
:36B::SETT//UNIT/200,00
:97A::SAFE//123456789
:16S:FIAC
:16R:SETDET
:22F::SETR//TRAD
:16R:SETPRTY
:95R::DEAG/SICV/4042
:16S:SETPRTY
:16R:SETPRTY
:95P::SELL//CITIFRPP
:97A::SAFE//123456789
:16S:SETPRTY
:16R:SETPRTY
:95P::PSET//SICVFRPP
:16S:SETPRTY
:16R:AMT
:19A::SETT//EUR123456,50
:16S:AMT
:16S:SETDET
:20:REFERENCE
:23B:CRED
-}
 * </pre>
 * 
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class MessageCreation2Example {

    /**
     * This example creates a new MT103 using MT and Field helper classes.
     */
    public static void main(String[] args) throws Exception {
		/*
		 * Create the MT class, it will be initialized as an outgoing message
		 * with normal priority
		 */
		final MT542 m = new MT542();
	
		/*
		 * Set sender and receiver BIC codes
		 */
		m.setSender("FOOSEDR0AXXX");
		m.setReceiver("FOORECV0XXXX");
	
		/*
		 * Add a field using comprehensive setters API, will use it later inside
		 * sequence A
		 */
		Field98A f98A = new Field98A()
			.setQualifier("PREP")
			.setDate(Calendar.getInstance());
	
		/*
		 * Start adding the message's fields in correct order, starting with
		 * general information sequence
		 */
		SequenceA A = MT542.SequenceA.newInstance(
		/*
		 * Add field using the complete literal value
		 */
		Field20C.tag(":SEME//2005071800000923"), 
		Field23G.tag("NEWM"), 
		f98A.asTag());
		/*
		 * Add sequence A to message
		 */
		m.append(A);
	
		/*
		 * trade details sequence B
		 */
		m.append(MT542.SequenceB.newInstance(
			Field98A.tag(":TRAD//20050714"), 
			Field98A.tag(":SETT//20050719"), 
			Field90B.tag(":DEAL//ACTU/EUR21,49"),
		        new Field35B().setQualifier("ISIN").setISIN("FR1234567890").setDescription("AXA UAP").asTag(),
		        Field70E.tag(":SPRO//4042")));
	
		/*
		 * financial instrument account sequence C
		 */
		m.append(MT542.SequenceC.newInstance(
			Field36B.tag(":SETT//UNIT/200,00"), 
			Field97A.tag(":SAFE//123456789")));
	
		/*
		 * settlement details: sequence E
		 */
		// use constant of Tag that marks the start of sequence
		m.append(MT542.SequenceE.START_TAG);
	
		m.append(Field22F.tag(":SETR//TRAD"));
	
		m.append(MT542.SequenceE1.newInstance(Field95R.tag(":DEAG/SICV/4042")));
	
		m.append(MT542.SequenceE1.newInstance(Field95P.tag(":SELL//CITIFRPP"), Field97A.tag(":SAFE//123456789")));
	
		m.append(MT542.SequenceE1.newInstance(Field95P.tag(":PSET//SICVFRPP")));
	
		m.append(MT542.SequenceE3.newInstance(Field19A.tag(":SETT//EUR123456,50")));

		// use constant of Tag that marks the end of sequence
		m.append(MT542.SequenceE.END_TAG);
	
		m.append(new Field20("REFERENCE"));
		m.append(new Field23B("CRED"));
	
		/*
		 * Create and print out the SWIFT FIN message string
		 */
		System.out.println(m.message());
    }
}
