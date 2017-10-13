package com.sabu.test.testpgp.tests;

import java.io.*;
import java.security.NoSuchProviderException;

import com.sabu.openpgp.utils.PgpHelperImpl;
import org.bouncycastle.openpgp.PGPException;
//import org.jdamico.bc.openpgp.utils.PgpHelperImpl;
//import org.jdamico.bc.openpgp.utils.RSAKeyPairGenerator;
import org.junit.Test;

public class TestPGP {

    private boolean isArmored = false;
    private String id = "testing";
    private String passwd = "test1234";
    private boolean integrityCheck = true;


    private String pubKeyFile = "C:\\Work\\IJSpace\\poc\\testPGP\\src\\test\\resources\\pgp-test-keys\\public.asc";
    private String privKeyFile = "C:\\Work\\IJSpace\\poc\\testPGP\\src\\test\\resources\\pgp-test-keys\\private.asc";


    private String plainTextFile = "C:\\Users\\vhuss\\Desktop\\TEST_SIM_INPUT\\test.txt"; //create a text file to be encripted, before run the tests
    private String cipherTextFile = "C:\\Users\\vhuss\\Desktop\\TEST_SIM_INPUT\\encrypt\\cypher-text.dat";
    private String decPlainTextFile = "C:\\Users\\vhuss\\Desktop\\TEST_SIM_INPUT\\encrypt\\dec-plain-text.txt";
    private String signatureFile = "C:\\Users\\vhuss\\Desktop\\TEST_SIM_INPUT\\encrypt\\signature.txt";


//    @Test
//    public void genKeyPair() throws InvalidKeyException, NoSuchProviderException, SignatureException, IOException, PGPException, NoSuchAlgorithmException {
//
//        RSAKeyPairGenerator rkpg = new RSAKeyPairGenerator();
//
//        Security.addProvider(new BouncyCastleProvider());
//
//        KeyPairGenerator    kpg = KeyPairGenerator.getInstance("RSA", "BC");
//
//        kpg.initialize(1024);
//
//        KeyPair                    kp = kpg.generateKeyPair();
//
//        FileOutputStream    out1 = new FileOutputStream(privKeyFile);
//        FileOutputStream    out2 = new FileOutputStream(pubKeyFile);
//
//        rkpg.exportKeyPair(out1, out2, kp.getPublic(), kp.getPrivate(), id, passwd.toCharArray(), isArmored);
//
//
//    }

    @Test
    public void encrypt() throws NoSuchProviderException, IOException, PGPException{
        FileInputStream pubKeyIs = new FileInputStream(pubKeyFile);
        FileOutputStream cipheredFileIs = new FileOutputStream(cipherTextFile);

        PgpHelperImpl.getInstance().encryptFile(cipheredFileIs, plainTextFile, PgpHelperImpl.getInstance().readPublicKey(pubKeyIs), isArmored, integrityCheck);
        cipheredFileIs.close();
        pubKeyIs.close();
    }

    @Test
    public void decrypt() throws Exception{

        FileInputStream cipheredFileIs = new FileInputStream(cipherTextFile);
        FileInputStream privKeyIn = new FileInputStream(privKeyFile);
        FileOutputStream plainTextFileIs = new FileOutputStream(decPlainTextFile);
        PgpHelperImpl.getInstance().decryptFile(cipheredFileIs, plainTextFileIs, privKeyIn, passwd.toCharArray());
        cipheredFileIs.close();
        plainTextFileIs.close();
        privKeyIn.close();
    }

    @Test
    public void signAndVerify() throws Exception{
        FileInputStream privKeyIn = new FileInputStream(privKeyFile);
        FileInputStream pubKeyIs = new FileInputStream(pubKeyFile);
        FileInputStream plainTextInput = new FileInputStream(plainTextFile);
        FileOutputStream signatureOut = new FileOutputStream(signatureFile);

        byte[] bIn = PgpHelperImpl.getInstance().inputStreamToByteArray(plainTextInput);
        byte[] sig = PgpHelperImpl.getInstance().createSignature(plainTextFile, privKeyIn, signatureOut, passwd.toCharArray(), true);
        PgpHelperImpl.getInstance().verifySignature(plainTextFile, sig, pubKeyIs);
    }
}
