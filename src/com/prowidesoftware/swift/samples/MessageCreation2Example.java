/*
 * Copyright (c) http://www.prowidesoftware.com/, 2013. All rights reserved.
 */
package com.prowidesoftware.swift.samples;

import java.util.Calendar;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.io.writer.FINWriterVisitor;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.field.Field16R;
import com.prowidesoftware.swift.model.field.Field16S;
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

/**
 * Example of message creation using high level API from WIFE.
 * An MT542 is created and converted to the SWIFT FIN format.
 * 
 * @author www.prowidesoftware.com
 */
public class MessageCreation2Example {

	/**
	 * This example creates a new MT103 using MT and Field helper classes.
	 */
	public static void main(String[] args) throws Exception {
		/*
		 * Create the MT class, it will be initialized as an outgoing message with normal priority
		 */
		final MT542 m = new MT542(new SwiftMessage(true));
		
		/*
		 * Set sender and receiver BIC codes 
		 */
		m.setSender("FOOSEDR0AXXX");
		m.setReceiver("FOORECV0XXXX");
		
		/*
		 * Start adding the message's fields in correct order, starting with general information sequence
		 */
		m.addField(new Field16R("GENL"));
		/*
		 * Add field using the complete literal value
		 */
		m.addField(new Field20C(":SEME//2005071800000923"));
		m.addField(new Field23G("NEWM"));
		/*
		 * Add a field using comprehensive setters API
		 */
		Field98A f98A = new Field98A();
		f98A.setQualifier("PREP");
		f98A.setDate(Calendar.getInstance());
		m.addField(f98A);
		
		m.addField(new Field16S("GENL"));
		
		/*
		 * trade details sequence
		 */
		m.addField(new Field16R("TRADDET"));
		m.addField(new Field98A(":TRAD//20050714"));
		m.addField(new Field98A(":SETT//20050719"));
		m.addField(new Field90B(":DEAL//ACTU/EUR21,49"));
		m.addField(new Field35B("ISIN FR1234567890"+FINWriterVisitor.SWIFT_EOL+"AXA UAP"));
		m.addField(new Field70E(":SPRO//4042"));
		m.addField(new Field16S("TRADDET"));
		
		/*
		 * financial instrument account
		 */
		m.addField(new Field16R("FIAC"));
		m.addField(new Field36B(":SETT//UNIT/200,00"));
		m.addField(new Field97A(":SAFE//123456789"));
		m.addField(new Field16S("FIAC"));
		
		/*
		 * settlement details
		 */
		m.addField(new Field16R("SETDET"));
		m.addField(new Field22F(":SETR//TRAD"));
		
		m.addField(new Field16R("SETPRTY"));
		m.addField(new Field95R(":DEAG/SICV/4042"));
		m.addField(new Field16S("SETPRTY"));
		
		m.addField(new Field16R("SETPRTY"));
		m.addField(new Field95P(":SELL//CITIFRPP"));
		m.addField(new Field97A(":SAFE//123456789"));
		m.addField(new Field16S("SETPRTY"));
		
		m.addField(new Field16R("SETPRTY"));
		m.addField(new Field95P(":PSET//SICVFRPP"));
		m.addField(new Field16S("SETPRTY"));
		
		m.addField(new Field16R("AMT"));
		m.addField(new Field19A(":SETT//EUR123456,50"));
		m.addField(new Field16S("AMT"));
		m.addField(new Field16S("SETDET"));

		
		m.addField(new Field20("REFERENCE"));
		m.addField(new Field23B("CRED"));
		
		/*
		 * Create and print out the SWIFT FIN message string
		 */
		System.out.println(m.FIN());
		
		/*
		 * Below text is the expected SWIFT FIN message content:
		 * 
		 * {1:F01FOOSEDR0AXXX0000000000}{2:I542FOORECV0XXXXN}{4:
		 * :16R:GENL
		 * :20C::SEME//2005071800000923
		 * :23G:NEWM
		 * :98A::PREP//20130729
		 * :16S:GENL
		 * :16R:TRADDET
		 * :98A::TRAD//20050714
		 * :98A::SETT//20050719
		 * :90B::DEAL//ACTU/EUR21,49
		 * :35B:ISIN FR1234567890FR1234567890
		 * AXA UAP
		 * :70E::SPRO//4042
		 * :16S:TRADDET
		 * :16R:FIAC
		 * :36B::SETT//UNIT/200,00
		 * :97A::SAFE//123456789
		 * :16S:FIAC
		 * :16R:SETDET
		 * :22F::SETR//TRAD
		 * :16R:SETPRTY
		 * :95R::DEAG/SICV/4042
		 * :16S:SETPRTY
		 * :16R:SETPRTY
		 * :95P::SELL//CITIFRPP
		 * :97A::SAFE//123456789
		 * :16S:SETPRTY
		 * :16R:SETPRTY
		 * :95P::PSET//SICVFRPP
		 * :16S:SETPRTY
		 * :16R:AMT
		 * :19A::SETT//EUR123456,50
		 * :16S:AMT
		 * :16S:SETDET
		 * :20:REFERENCE
		 * :23B:CRED
		 * -}
		 * 
		 */
        new SwiftParser(m.FIN()).message();
	}
}
