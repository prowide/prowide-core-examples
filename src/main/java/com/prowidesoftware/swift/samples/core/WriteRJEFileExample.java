/*
 * Copyright 2006-2021 Prowide
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.prowidesoftware.swift.samples.core;

import com.prowidesoftware.swift.io.RJEWriter;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field23B;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

/**
 * Example to create some messages and serialize them into an RJE file.
 *
 * <p>The RJE writer is scheduled for release in version 7.8
 * In the meantime it is available on source code, or upon request.
 */
public class WriteRJEFileExample {

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
        RJEWriter writer = new RJEWriter("/tmp/test.rje");

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
