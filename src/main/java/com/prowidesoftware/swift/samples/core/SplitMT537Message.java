package com.prowidesoftware.swift.samples.core;

import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.mt5xx.MT537;
import com.prowidesoftware.swift.utils.Lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://prowide.atlassian.net/browse/PW-790
 * https://prowide.atlassian.net/browse/PW-919
 */
public class SplitMT537Message {

    /**
     * This sample runs the split by SequenceD with size limit, validating the output and logging the split messages size and values per messages
     */
    public static void main(String args[]) throws IOException {
        //Parse the original large input MT537
        String mt537String = Lib.readResource("MT537_large.fin");
        MT537 originalMT537 = MT537.parse(mt537String);

        //Message greater than 10k split
        if (originalMT537.message().length() > 10000) {
            List<MT537> messages = splitWithSizeCheckMT537(originalMT537);
            assertSizeMT537(messages, originalMT537);
        }

    }

    /**
     * @param original the large MT537
     * @return a List<MT537> where all messages are less than 10k characters
     */
    private static List<MT537> splitWithSizeCheckMT537(MT537 original) {
        List<MT537> messages = new ArrayList<>();
        MT537 current = createAndCopyCommonContentMT537(original);
        addSequencesToMessage(current, original, messages);
        return messages;
    }

    /**
     * @param current  the current new MT537
     * @param original the original large MT537
     * @param messages empty list of messages, will contain all the messages resulting from the split
     */
    private static void addSequencesToMessage(MT537 current, MT537 original, List<MT537> messages) {

        //First Message include SequenceB and SequenceC.
        if (messages.size() == 0) {
            //SeqB (Optional)
            for (MT537.SequenceB seqB : original.getSequenceBList()) {
                current.append(seqB);
            }
            //SeqC (Optional)
            for (MT537.SequenceC seqC : original.getSequenceCList()) {
                current.append(seqC);
            }
        }

        //If the message with SeqA, SeqB, and Seq C is less than 10k. Starts to add SeqD (Optional)
        if (current.message().length() < 10000) {
            for (MT537.SequenceD seqD : original.getSequenceDList()) {
                if (canAppendMoreSequences(current, seqD)) {
                    current.append(seqD);
                    continue;
                } else {
                    messages.add(current);
                    current = createAndCopyCommonContentMT537(original);
                    current.append(seqD);
                }
            }
        } else {
            messages.add(current);
            addSequencesToMessage(createAndCopyCommonContentMT537(original), original, messages);
        }

        //If the last one message
        if (current.message().length() < 10000) {
            messages.add(current);
        }

        //Counter of MT537 with sequences
        int mt537Index = 0;
        int sequencesD = 0;
        int page = 0;

        //Add SequenceE into the last message.
        addSequenceEIntoLastMessage(original, messages);

        //Set Tags 28E and 13A to
        for (MT537 createdMessage : messages) {
            sequencesD = sequencesD + createdMessage.getSequenceDList().size();
            page = page + 1;
            String leftPaddedPage = padLeftZeros(String.valueOf(page), 3);
            createdMessage.getSwiftMessage().getBlock4().getTagByName("28E").setValue(leftPaddedPage + "/MORE");
            createdMessage.getSwiftMessage().getBlock4().getTagByName("13A").setValue("STAT//" + leftPaddedPage);
        }

        //Get the last message created and set Tag 28E value with "LAST"
        messages.get(messages.size() - 1).getSwiftMessage().getBlock4().getTagByName("28E").setValue(padLeftZeros(String.valueOf(page), 3) + "/LAST");

        //Print for each MT537 all Sequences size and the Tags Value modified
        for (MT537 createdMessage : messages) {
            System.out.println("MT537 number " + mt537Index++ + " SeqA: " + createdMessage.getSequenceA().size() +
                    " SeqB: " + createdMessage.getSequenceBList().size() +
                    " SeqC " + createdMessage.getSequenceCList().size() +
                    " SeqD " + createdMessage.getSequenceDList().size() +
                    " SeqE " + createdMessage.getSequenceEList().size());

            System.out.println("28E: " + createdMessage.getSwiftMessage().getBlock4().getFieldByName("28E").getValue());
            System.out.println("13A: " + createdMessage.getSwiftMessage().getBlock4().getFieldByName("13A").getValue());
            System.out.println("20C: " + createdMessage.getSwiftMessage().getBlock4().getFieldByName("20C").getValue());
            System.out.println("--------------");
        }

    }

    private static void addSequenceEIntoLastMessage(MT537 original, List<MT537> messages) {
        MT537 lastMessage = messages.get(messages.size() - 1);
        MT537 newMessageToAddSeqE;

        if (lastMessage.message().length() < 10000) {
            newMessageToAddSeqE = lastMessage;
        } else {
            newMessageToAddSeqE = createAndCopyCommonContentMT537(original);
            messages.add(newMessageToAddSeqE);
        }

        for (MT537.SequenceE seqE : original.getSequenceEList()) {
            if (canAppendMoreSequences(newMessageToAddSeqE, seqE)) {
                newMessageToAddSeqE.append(seqE);
                continue;
            } else {
                messages.add(newMessageToAddSeqE);
                newMessageToAddSeqE = createAndCopyCommonContentMT537(original);
                newMessageToAddSeqE.append(seqE);
            }
        }

    }

    private static boolean canAppendMoreSequences(MT537 current, SwiftTagListBlock currentSeqToAdd) {
        MT537 copyOfCurrent = MT537.parse(current.message());
        copyOfCurrent.append(currentSeqToAdd);
        if (copyOfCurrent.message().length() > 10000) {
            return false;
        }
        return true;
    }


    private static MT537 createAndCopyCommonContentMT537(MT537 original) {
        MT537 new_mt537 = new MT537();
        //All messages with Block 1,2,3 y 5.
        new_mt537.getSwiftMessage().setBlock1(original.getSwiftMessage().getBlock1());
        new_mt537.getSwiftMessage().setBlock2(original.getSwiftMessage().getBlock2());
        new_mt537.getSwiftMessage().setBlock3(original.getSwiftMessage().getBlock3());
        new_mt537.getSwiftMessage().setBlock5(original.getSwiftMessage().getBlock5());
        new_mt537.getSwiftMessage().setBlock4(new SwiftBlock4());
        new_mt537.getSwiftMessage().getBlock4().append(original.getSequenceA());

        // force deep copy of objects
        MT537 mt537Copy = MT537.parse(new_mt537.message());
        return mt537Copy;
    }


    private static void assertSizeMT537(List<MT537> messages, MT537 original) {
        System.out.println("Total messages after split: " + messages.size());
        int count = 1;
        int seqDSummary = 0;
        for (MT537 part : messages) {
            // log split size
            System.out.println(count + " size=" + part.message().length());
            count++;
            seqDSummary = seqDSummary + part.getSequenceDList().size();
        }

        System.out.println("Total of SeqD into the original message : " + original.getSequenceDList().size());
        System.out.println("Total of SeqD resulting of all messages : " + seqDSummary);
    }


    private static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

}