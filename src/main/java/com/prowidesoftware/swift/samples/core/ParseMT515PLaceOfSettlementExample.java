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

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.field.Field95P;
import com.prowidesoftware.swift.model.mt.mt5xx.MT515;

import java.io.IOException;

/**
 * Parses an MT515 into a model object and then uses the getters API to retrieve a particular field.
 * First example uses the specific MT515 model and its getters, while the second option uses the generic model.
 * <p>
 * Running this program produces the following output:
 * <pre>
 * Place of settlement: CRSTGB22
 * </pre>
 */
public class ParseMT515PLaceOfSettlementExample {

    public static void main(String[] args) throws IOException {
        String fin = "{1:F01AAAAIT2TAXXX8236800622}{2:O5151648131111BBBBLULLCFND22278474801311111648N}{4:\n" +
                ":16R:GENL\n" +
                ":20C::SEME//FRTJ123456789\n" +
                ":23G:NEWM\n" +
                ":22F::TRTR//TRAD\n" +
                ":16R:LINK\n" +
                ":13A::LINK//514\n" +
                ":20C::RELA//0405D012AA\n" +
                ":16S:LINK\n" +
                ":16S:GENL\n" +
                ":16R:CONFDET\n" +
                ":98A::TRAD//20210112\n" +
                ":98A::SETT//20210117\n" +
                ":90A::DEAL//PRCT/101,001283\n" +
                ":22F::PRIC//AVER\n" +
                ":22H::PAYM//APMT\n" +
                ":22H::BUSE//BUYI\n" +
                ":16R:CONFPRTY\n" +
                ":95P::INVE//FUNANIC1\n" +
                ":16S:CONFPRTY\n" +
                ":16R:CONFPRTY\n" +
                ":95P::BUYR//MGTCDE55\n" +
                ":16S:CONFPRTY\n" +
                ":16R:CONFPRTY\n" +
                ":95P::SELL//CFPIDEFF\n" +
                ":16S:CONFPRTY\n" +
                ":36B::CONF//FAMT/4000000,\n" +
                ":35B:ISIN GB0123412345\n" +
                ":16S:CONFDET\n" +
                ":16R:SETDET\n" +
                ":22F::SETR//TRAD\n" +
                ":16R:SETPRTY\n" +
                ":95R::DEAG/CRST/111\n" +
                ":16S:SETPRTY\n" +
                ":16R:SETPRTY\n" +
                ":95P::SELL//CFPIDEFF\n" +
                ":97A::SAFE//1234567\n" +
                ":16S:SETPRTY\n" +
                ":16R:SETPRTY\n" +
                ":95P::PSET//CRSTGB22\n" +
                ":16S:SETPRTY\n" +
                ":16R:AMT\n" +
                ":19A::DEAL//GBP4040051,32\n" +
                ":16S:AMT\n" +
                ":16R:AMT\n" +
                ":19A::ACRU//GBP7000,\n" +
                ":16S:AMT\n" +
                ":16R:AMT\n" +
                ":19A::EXEC//GBP100,\n" +
                ":16S:AMT\n" +
                ":16R:AMT\n" +
                ":19A::SETT//GBP4047151,32\n" +
                ":16S:AMT\n" +
                ":16S:SETDET\n" +
                "-}";

        // option 1 using the specific model class
        MT515 mt = MT515.parse(fin);
        mt.getField95P().forEach(f -> {
            if ("PSET".equals(f.getQualifier())) {
                System.out.println("Place of settlement: " + f.getIdentifierCode());
            }
        });

        // option 2 using the generic model class
        SwiftMessage sm = SwiftMessage.parse(fin);
        sm.getBlock4().getFieldsByName(Field95P.NAME, "PSET").forEach(f -> {
            Field95P field95P = (Field95P) f;
            System.out.println("Place of settlement: " + field95P.getIdentifierCode());
        });

    }
}
