package com.shpp.p2p.cs.ibilash.assignment14;


import java.io.IOException;

/**
 *  defines archive or unpack process .
 */
public class ProcessSelection {
    /**
     * name of incoming and outgoing file
      */
    private String inName,outName = "";

    /**
     * use this parameter if args is empty
     */
    private final String PARAMETER = "test.txt";

    /**
     * take args and use takeParameter who defines archive or unpack and use method processSelection .
     * @param args input parameter , mark and file name
     */
    ProcessSelection(String[] args) {
        processSelection(takeParameter(args));
    }


    /**
     * defines archive or unpack and used classes Archive and Unpack .
     */
   private void processSelection(String param) {
        //size input and output files
        int sizeInPutFile = 1;
        int sizeOutPutFile = 1;
        try {
            long startTime = System.currentTimeMillis();
            if (param.equals("-a")) {
                System.out.println("archiving");
                Archive rd = new Archive(inName, outName);
                sizeOutPutFile= rd.compressionData();
                sizeInPutFile = rd.countInPutByte;


            }
            if (param.equals("-u")) {
                System.out.println("unzip");
               Unpack decompression = new Unpack(inName, outName);
                sizeInPutFile = decompression.unpackProcess();
                sizeOutPutFile = decompression.outBytes;
            }

            long resultTime = (System.currentTimeMillis() - startTime) ;
            System.out.println("effectivity : " + ((sizeOutPutFile * 100) / sizeInPutFile) + " %" + "\n" +
                    "process time:       " + resultTime + " sec" + "\n" +
                    "size of input file :" + sizeInPutFile + "  bytes" + "\n" +
                    "size of outgoing file :" + sizeOutPutFile + "  bytes");
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }

    /**
     * processing input parameter of defining archiving or unpacking
     *
     * @param args input marks and file names
     * @return mark archive or unpack
     */
   private String takeParameter(String[] args) {
        String string = args.length == 0 ? PARAMETER : args[0];
        if (args.length <= 1) {
            if (string.contains(".par")) {
                inName = string;
                outName = string.substring(0, string.length() - ".par".length());
                return "-u";
            } else {
                inName = string;
                outName = string + ".par";
                return "-a";
            }
        }
        if (args.length > 1) {
            if (args[0].contains("-a")) {
                inName = args[1];
                outName = args[2];
                return "-a";
            }
            if (args[0].contains("-u")) {
                inName = args[1];
                outName = args[2];
                return "-u";
            }
        }

        return "";
    }
}
