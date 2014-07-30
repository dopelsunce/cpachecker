/* The following code was generated by JFlex 1.4.3 on [date omitted] */

/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2011  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 *  CPAchecker web page:
 *    http://cpachecker.sosy-lab.org
 */

package org.sosy_lab.cpachecker.core.algorithm.tiger.fql.parser;

import static org.sosy_lab.cpachecker.core.algorithm.tiger.fql.parser.FQLSym.*;

import java.io.IOException;

import java_cup.runtime.Symbol;

@SuppressWarnings("all")
/**
 * This class is a scanner generated by
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on [date omitted] from the specification file
 * <tt>src/org/sosy_lab/cpachecker/fllesh/fql2/parser/FQL.lex</tt>
 */
final class FQLLexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
      0, 0
  };

  /**
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED =
      "\11\0\2\53\25\0\1\53\1\43\1\47\1\0\1\30\2\0\1\52" +
          "\1\1\1\2\1\46\1\45\1\3\1\0\1\44\1\0\12\13\2\0" +
          "\1\42\1\41\1\40\1\0\1\6\1\20\1\21\1\14\1\5\1\11" +
          "\1\7\1\32\1\34\1\4\1\51\1\33\1\10\1\17\1\12\1\15" +
          "\1\31\1\51\1\24\1\22\1\23\1\16\1\50\1\51\1\27\1\25" +
          "\1\51\3\0\1\26\1\35\1\0\32\51\1\36\1\0\1\37\uff82\0";

  /**
   * Translates characters to character classes
   */
  private static final char[] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int[] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
      "\1\0\1\1\1\2\1\3\2\4\1\0\2\4\1\5" +
          "\3\4\1\6\1\7\1\4\1\10\1\11\1\12\1\0" +
          "\1\13\1\0\1\14\1\15\1\16\1\17\1\0\1\20" +
          "\1\21\1\22\4\0\1\23\3\0\10\4\1\24\1\25" +
          "\1\26\1\27\1\30\1\4\13\0\13\4\15\0\11\4" +
          "\1\31\1\4\1\0\1\32\1\33\1\34\2\0\1\35" +
          "\2\0\1\36\2\0\1\37\1\4\1\40\2\4\1\41" +
          "\1\42\2\4\1\43\1\4\1\0\1\44\2\0\1\45" +
          "\2\0\6\4\1\0\1\46\2\0\1\47\2\4\1\50" +
          "\1\4\1\51\1\4\3\0\2\4\1\52\1\53\3\0" +
          "\2\4\3\0\1\4\1\54\4\0\1\4\4\0\1\4" +
          "\1\55\3\0\1\4\1\56\2\0\1\4\1\57\1\0" +
          "\1\4\1\60\1\61";

  private static int[] zzUnpackAction() {
    int[] result = new int[186];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int[] result) {
    int i = 0; /* index in packed string  */
    int j = offset; /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do {
        result[j++] = value;
      } while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int[] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
      "\0\0\0\54\0\54\0\54\0\130\0\204\0\260\0\334" +
          "\0\u0108\0\u0134\0\u0160\0\u018c\0\u01b8\0\54\0\54\0\u01e4" +
          "\0\54\0\54\0\u0210\0\u023c\0\u0268\0\u0294\0\54\0\54" +
          "\0\54\0\54\0\u02c0\0\54\0\204\0\u02ec\0\u0318\0\u0344" +
          "\0\u0370\0\u039c\0\u03c8\0\u03f4\0\u0420\0\u044c\0\u0478\0\u04a4" +
          "\0\u04d0\0\u04fc\0\u0528\0\u0554\0\u0580\0\u05ac\0\54\0\54" +
          "\0\54\0\54\0\54\0\u05d8\0\u0604\0\u0630\0\u065c\0\u0688" +
          "\0\u06b4\0\u06e0\0\u070c\0\u0738\0\u0764\0\u0790\0\u07bc\0\u07e8" +
          "\0\u0814\0\u0840\0\u086c\0\u0898\0\u08c4\0\u08f0\0\u091c\0\u0948" +
          "\0\u0974\0\u09a0\0\u09cc\0\u09f8\0\u0a24\0\u0a50\0\u0a7c\0\u0aa8" +
          "\0\u0ad4\0\u0b00\0\u0b2c\0\u0b58\0\u0b84\0\u0bb0\0\u0bdc\0\u0c08" +
          "\0\u0c34\0\u0c60\0\u0c8c\0\u0cb8\0\u0ce4\0\u0d10\0\u0d3c\0\u0d68" +
          "\0\204\0\u0d94\0\u0dc0\0\54\0\54\0\54\0\u0dec\0\u0e18" +
          "\0\54\0\u0e44\0\u0e70\0\u0e9c\0\u0ec8\0\u0ef4\0\204\0\u0f20" +
          "\0\204\0\u0f4c\0\u0f78\0\204\0\204\0\u0fa4\0\u0fd0\0\204" +
          "\0\u0ffc\0\u1028\0\54\0\u1054\0\u1080\0\54\0\u10ac\0\u10d8" +
          "\0\u1104\0\u1130\0\u115c\0\u1188\0\u11b4\0\u11e0\0\u120c\0\54" +
          "\0\u1238\0\u1264\0\54\0\u1290\0\u12bc\0\204\0\u12e8\0\204" +
          "\0\u1314\0\u1340\0\u136c\0\u1398\0\u13c4\0\u13f0\0\204\0\204" +
          "\0\u141c\0\u1448\0\u1474\0\u14a0\0\u14cc\0\u14f8\0\u1524\0\u1550" +
          "\0\u157c\0\204\0\u15a8\0\u15d4\0\u1600\0\u162c\0\u1658\0\u1684" +
          "\0\u16b0\0\u16dc\0\u1708\0\u1734\0\54\0\u1760\0\u178c\0\u17b8" +
          "\0\u17e4\0\54\0\u1810\0\u183c\0\u1868\0\54\0\u1894\0\u18c0" +
          "\0\54\0\204";

  private static int[] zzUnpackRowMap() {
    int[] result = new int[186];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int[] result) {
    int i = 0; /* index in packed string  */
    int j = offset; /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int[] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
      "\1\0\1\2\1\3\1\4\1\5\1\6\1\7\2\6" +
          "\1\10\1\11\1\12\1\13\1\6\1\14\3\6\1\15" +
          "\3\6\1\16\1\6\1\17\1\20\4\6\1\21\1\22" +
          "\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32" +
          "\2\6\1\33\1\34\60\0\1\6\1\35\1\0\3\6" +
          "\1\36\13\6\1\0\1\6\1\0\5\6\12\0\2\6" +
          "\6\0\2\6\1\0\17\6\1\0\1\6\1\0\5\6" +
          "\12\0\2\6\7\0\1\37\1\0\1\40\1\41\1\42" +
          "\1\0\1\43\1\44\4\0\1\45\2\0\1\46\33\0" +
          "\1\6\1\47\1\0\3\6\1\50\13\6\1\0\1\6" +
          "\1\0\5\6\12\0\2\6\6\0\2\6\1\0\6\6" +
          "\1\51\10\6\1\0\1\6\1\0\5\6\12\0\2\6" +
          "\15\0\1\12\44\0\2\6\1\0\6\6\1\52\10\6" +
          "\1\0\1\6\1\0\5\6\12\0\2\6\6\0\2\6" +
          "\1\0\3\6\1\53\13\6\1\0\1\6\1\0\5\6" +
          "\12\0\2\6\6\0\2\6\1\0\2\6\1\54\14\6" +
          "\1\0\1\6\1\0\5\6\12\0\2\6\6\0\2\6" +
          "\1\0\11\6\1\55\3\6\1\56\1\6\1\0\1\6" +
          "\1\0\5\6\12\0\2\6\43\0\1\57\53\0\1\60" +
          "\53\0\1\61\53\0\1\62\12\0\52\33\1\63\1\33" +
          "\4\0\2\6\1\0\14\6\1\64\2\6\1\0\1\6" +
          "\1\0\5\6\12\0\2\6\13\0\1\65\46\0\1\66" +
          "\11\0\1\67\41\0\1\70\13\0\1\71\45\0\1\72" +
          "\14\0\1\73\37\0\1\43\55\0\1\74\2\0\1\75" +
          "\53\0\1\76\44\0\1\77\46\0\2\6\1\0\17\6" +
          "\1\0\1\6\1\0\1\6\1\100\3\6\12\0\2\6" +
          "\6\0\2\6\1\0\5\6\1\101\11\6\1\0\1\6" +
          "\1\0\5\6\12\0\2\6\6\0\1\6\1\102\1\0" +
          "\17\6\1\0\1\6\1\0\5\6\12\0\2\6\6\0" +
          "\2\6\1\0\10\6\1\103\6\6\1\0\1\6\1\0" +
          "\5\6\12\0\1\104\1\6\6\0\1\105\1\6\1\0" +
          "\17\6\1\0\1\6\1\0\5\6\12\0\2\6\6\0" +
          "\2\6\1\0\14\6\1\106\2\6\1\0\1\6\1\0" +
          "\5\6\12\0\2\6\6\0\2\6\1\0\13\6\1\107" +
          "\1\110\2\6\1\0\1\6\1\0\5\6\12\0\2\6" +
          "\6\0\2\6\1\0\2\6\1\111\14\6\1\0\1\6" +
          "\1\0\5\6\12\0\2\6\6\0\2\6\1\0\2\6" +
          "\1\112\14\6\1\0\1\6\1\0\5\6\12\0\2\6" +
          "\16\0\1\113\47\0\1\114\55\0\1\115\53\0\1\116" +
          "\62\0\1\117\55\0\1\120\34\0\1\121\24\0\1\122" +
          "\32\0\1\123\1\0\1\124\51\0\1\125\65\0\1\126" +
          "\63\0\1\127\25\0\2\6\1\0\2\6\1\130\14\6" +
          "\1\0\1\6\1\0\5\6\12\0\2\6\6\0\2\6" +
          "\1\0\1\6\1\131\15\6\1\0\1\6\1\0\5\6" +
          "\12\0\2\6\6\0\2\6\1\0\2\6\1\132\14\6" +
          "\1\0\1\6\1\0\5\6\12\0\2\6\6\0\2\6" +
          "\1\0\17\6\1\0\1\6\1\0\1\133\4\6\12\0" +
          "\2\6\6\0\2\6\1\0\2\6\1\134\14\6\1\0" +
          "\1\6\1\0\5\6\12\0\2\6\6\0\2\6\1\0" +
          "\6\6\1\135\10\6\1\0\1\6\1\0\5\6\12\0" +
          "\2\6\6\0\2\6\1\0\10\6\1\136\6\6\1\0" +
          "\1\6\1\0\5\6\12\0\2\6\6\0\2\6\1\0" +
          "\13\6\1\137\3\6\1\0\1\6\1\0\5\6\12\0" +
          "\2\6\6\0\2\6\1\0\17\6\1\0\1\6\1\0" +
          "\3\6\1\140\1\6\12\0\2\6\6\0\1\6\1\141" +
          "\1\0\17\6\1\0\1\6\1\0\5\6\12\0\2\6" +
          "\6\0\2\6\1\0\15\6\1\142\1\6\1\0\1\6" +
          "\1\0\5\6\12\0\2\6\6\0\1\143\60\0\1\144" +
          "\56\0\1\145\50\0\1\146\53\0\1\147\66\0\1\150" +
          "\52\0\1\17\54\0\1\151\45\0\1\152\42\0\1\153" +
          "\56\0\1\154\47\0\1\155\60\0\1\156\46\0\2\6" +
          "\1\0\13\6\1\157\3\6\1\0\1\6\1\0\5\6" +
          "\12\0\2\6\6\0\2\6\1\0\6\6\1\160\10\6" +
          "\1\0\1\6\1\0\5\6\12\0\2\6\6\0\2\6" +
          "\1\0\13\6\1\161\3\6\1\0\1\6\1\0\5\6" +
          "\12\0\2\6\6\0\2\6\1\0\1\6\1\162\4\6" +
          "\1\163\10\6\1\0\1\6\1\0\5\6\12\0\2\6" +
          "\6\0\2\6\1\0\15\6\1\164\1\6\1\0\1\6" +
          "\1\0\5\6\12\0\2\6\6\0\2\6\1\0\3\6" +
          "\1\165\13\6\1\0\1\6\1\0\5\6\12\0\2\6" +
          "\6\0\1\166\1\6\1\0\17\6\1\0\1\6\1\0" +
          "\5\6\12\0\2\6\6\0\1\167\1\6\1\0\17\6" +
          "\1\0\1\6\1\0\5\6\12\0\2\6\6\0\2\6" +
          "\1\0\13\6\1\170\3\6\1\0\1\6\1\0\5\6" +
          "\12\0\2\6\6\0\2\6\1\0\13\6\1\171\3\6" +
          "\1\0\1\6\1\0\5\6\12\0\2\6\24\0\1\172" +
          "\41\0\1\173\70\0\1\16\45\0\1\174\40\0\1\175" +
          "\71\0\1\176\45\0\1\177\66\0\1\200\30\0\2\6" +
          "\1\0\13\6\1\201\3\6\1\0\1\6\1\0\5\6" +
          "\12\0\2\6\6\0\2\6\1\0\2\6\1\202\14\6" +
          "\1\0\1\6\1\0\5\6\12\0\2\6\6\0\2\6" +
          "\1\0\13\6\1\203\3\6\1\0\1\6\1\0\5\6" +
          "\12\0\2\6\6\0\2\6\1\0\3\6\1\204\13\6" +
          "\1\0\1\6\1\0\5\6\12\0\2\6\6\0\2\6" +
          "\1\0\3\6\1\205\13\6\1\0\1\6\1\0\5\6" +
          "\12\0\2\6\6\0\2\6\1\0\2\6\1\206\14\6" +
          "\1\0\1\6\1\0\5\6\12\0\2\6\6\0\1\207" +
          "\61\0\1\210\64\0\1\211\51\0\1\212\63\0\1\213" +
          "\26\0\1\214\1\6\1\0\17\6\1\0\1\6\1\0" +
          "\5\6\12\0\2\6\6\0\2\6\1\0\10\6\1\215" +
          "\6\6\1\0\1\6\1\0\5\6\12\0\2\6\6\0" +
          "\2\6\1\0\2\6\1\216\14\6\1\0\1\6\1\0" +
          "\5\6\12\0\2\6\6\0\2\6\1\0\7\6\1\217" +
          "\7\6\1\0\1\6\1\0\5\6\12\0\2\6\6\0" +
          "\2\6\1\0\17\6\1\0\1\6\1\0\1\6\1\220" +
          "\3\6\12\0\2\6\6\0\2\6\1\0\5\6\1\221" +
          "\11\6\1\0\1\6\1\0\5\6\12\0\2\6\17\0" +
          "\1\222\42\0\1\223\57\0\1\224\47\0\2\6\1\0" +
          "\3\6\1\225\13\6\1\0\1\6\1\0\5\6\12\0" +
          "\2\6\6\0\2\6\1\0\2\6\1\226\14\6\1\0" +
          "\1\6\1\0\5\6\12\0\2\6\6\0\2\6\1\0" +
          "\13\6\1\227\3\6\1\0\1\6\1\0\5\6\12\0" +
          "\2\6\6\0\2\6\1\0\14\6\1\230\2\6\1\0" +
          "\1\6\1\0\5\6\12\0\2\6\14\0\1\231\56\0" +
          "\1\232\53\0\1\233\42\0\2\6\1\0\17\6\1\0" +
          "\1\6\1\0\1\6\1\234\3\6\12\0\2\6\6\0" +
          "\2\6\1\0\3\6\1\235\13\6\1\0\1\6\1\0" +
          "\5\6\12\0\2\6\13\0\1\236\54\0\1\237\55\0" +
          "\1\240\43\0\2\6\1\0\17\6\1\0\1\6\1\0" +
          "\4\6\1\241\12\0\2\6\6\0\2\6\1\0\14\6" +
          "\1\242\2\6\1\0\1\6\1\0\5\6\12\0\2\6" +
          "\7\0\1\243\57\0\1\244\20\0\1\245\54\0\1\246" +
          "\24\0\2\6\1\0\13\6\1\247\3\6\1\0\1\6" +
          "\1\0\5\6\12\0\2\6\34\0\1\250\26\0\1\251" +
          "\72\0\1\252\40\0\1\253\46\0\2\6\1\0\5\6" +
          "\1\254\11\6\1\0\1\6\1\0\5\6\12\0\2\6" +
          "\13\0\1\255\74\0\1\256\41\0\1\257\45\0\1\260" +
          "\45\0\2\6\1\0\6\6\1\261\10\6\1\0\1\6" +
          "\1\0\5\6\12\0\2\6\13\0\1\262\73\0\1\263" +
          "\45\0\1\264\34\0\2\6\1\0\17\6\1\0\1\6" +
          "\1\0\1\265\4\6\12\0\2\6\36\0\1\266\43\0" +
          "\1\267\33\0\2\6\1\0\2\6\1\270\14\6\1\0" +
          "\1\6\1\0\5\6\12\0\2\6\27\0\1\271\32\0" +
          "\2\6\1\0\13\6\1\272\3\6\1\0\1\6\1\0" +
          "\5\6\12\0\2\6\2\0";

  private static int[] zzUnpackTrans() {
    int[] result = new int[6380];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int[] result) {
    int i = 0; /* index in packed string  */
    int j = offset; /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do {
        result[j++] = value;
      } while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
      "Unkown internal scanner error",
      "Error: could not match input",
      "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
      "\1\0\3\11\2\1\1\0\6\1\2\11\1\1\2\11" +
          "\1\1\1\0\1\1\1\0\4\11\1\0\1\11\2\1" +
          "\4\0\1\1\3\0\10\1\5\11\1\1\13\0\13\1" +
          "\15\0\13\1\1\0\3\11\2\0\1\11\2\0\1\1" +
          "\2\0\13\1\1\0\1\11\2\0\1\11\2\0\6\1" +
          "\1\0\1\11\2\0\1\11\6\1\3\0\4\1\3\0" +
          "\2\1\3\0\2\1\4\0\1\1\4\0\1\1\1\11" +
          "\3\0\1\1\1\11\2\0\1\1\1\11\1\0\1\1" +
          "\1\11\1\1";

  private static int[] zzUnpackAttribute() {
    int[] result = new int[186];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int[] result) {
    int i = 0; /* index in packed string  */
    int j = offset; /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do {
        result[j++] = value;
      } while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the
   * matched text
   */
  private int yycolumn;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  private Symbol sym(int type)
  {
    return sym(type, yytext());
  }

  private Symbol sym(int type, Object value)
  {
    //System.out.println(value);

    return new Symbol(type, yyline, yycolumn, value);
  }

  private void error()
      throws IOException
  {
    throw new IOException("illegal text at line = " + yyline + ", column = " + yycolumn + ", text = '" + yytext() + "'");
  }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  FQLLexer(java.io.Reader in) {
    // TODO: code that goes to constructor
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  FQLLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /**
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char[] zzUnpackCMap(String packed) {
    char[] map = new char[0x10000];
    int i = 0; /* index in packed string  */
    int j = 0; /* index in unpacked array */
    while (i < 120) {
      int count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do {
        map[j++] = value;
      } while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
          zzBuffer, 0,
          zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
        zzBuffer.length - zzEndRead);

    if (numRead > 0) {
      zzEndRead += numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }
    }

    // numRead < 0
    return true;
  }


  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; /* indicate end of file */
    zzEndRead = zzStartRead; /* invalidate buffer    */

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL = true;
    zzAtEOF = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead + pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos - zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number) {
    if (number > yylength()) {
      zzScanError(ZZ_PUSHBACK_2BIG);
    }

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  @Override
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;
    char[] zzCMapL = ZZ_CMAP;

    int[] zzTransL = ZZ_TRANS;
    int[] zzRowMapL = ZZ_ROWMAP;
    int[] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL; zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR) {
            zzR = false;
          } else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL) {
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        } else if (zzAtEOF) {
          zzPeek = false;
        } else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) {
            zzPeek = false;
          } else {
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
          }
        }
        if (zzPeek) {
          yyline--;
        }
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = zzBufferL[zzCurrentPosL++];
          } else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos = zzCurrentPosL;
            zzMarkedPos = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL = zzCurrentPos;
            zzMarkedPosL = zzMarkedPos;
            zzBufferL = zzBuffer;
            zzEndReadL = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[zzRowMapL[zzState] + zzCMapL[zzInput]];
          if (zzNext == -1) {
            break zzForAction;
          }
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ((zzAttributes & 1) == 1) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ((zzAttributes & 8) == 8) {
              break zzForAction;
            }
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
      case 32: {
        return sym(TOK_NODECOV);
      }
      case 50:
        break;
      case 44: {
        return sym(TOK_COMPLEMENT);
      }
      case 51:
        break;
      case 19: {
        return sym(TOK_LINE_ABBREV, Integer.valueOf(yytext().substring(1, yylength())));
      }
      case 52:
        break;
      case 27: {
        return sym(TOK_FUNC);
      }
      case 53:
        break;
      case 30: {
        return sym(TOK_CALL);
      }
      case 54:
        break;
      case 14: {
        return sym(TOK_KLEENE);
      }
      case 55:
        break;
      case 13: {
        return sym(TOK_ALTERNATIVE);
      }
      case 56:
        break;
      case 18: {
        return sym(TOK_IN);
      }
      case 57:
        break;
      case 36: {
        return sym(TOK_LABEL);
      }
      case 58:
        break;
      case 21: {
        return sym(TOK_EQ);
      }
      case 59:
        break;
      case 28: {
        return sym(TOK_LINE);
      }
      case 60:
        break;
      case 33: {
        return sym(TOK_COVER);
      }
      case 61:
        break;
      case 1: {
        return sym(TOK_L_PARENTHESIS);
      }
      case 62:
        break;
      case 48: {
        return sym(TOK_BASICBLOCKENTRY);
      }
      case 63:
        break;
      case 9: {
        return sym(TOK_R_BRACE);
      }
      case 64:
        break;
      case 26: {
        return sym(TOK_FILE);
      }
      case 65:
        break;
      case 38: {
        return sym(TOK_COLUMN);
      }
      case 66:
        break;
      case 23: {
        return sym(TOK_NEQ);
      }
      case 67:
        break;
      case 4: {
        return sym(TOK_C_IDENT, yytext());
      }
      case 68:
        break;
      case 31: {
        return sym(TOK_EDGECOV);
      }
      case 69:
        break;
      case 40: {
        return sym(TOK_COMPOSE);
      }
      case 70:
        break;
      case 47: {
        return sym(TOK_CONDITIONGRAPH);
      }
      case 71:
        break;
      case 35: {
        return sym(TOK_PATHCOV);
      }
      case 72:
        break;
      case 16: { /* NOOP */
      }
      case 73:
        break;
      case 42: {
        return sym(TOK_SETMINUS);
      }
      case 74:
        break;
      case 20: {
        return sym(TOK_GREATER_OR_EQ);
      }
      case 75:
        break;
      case 37: {
        return sym(TOK_CALLS);
      }
      case 76:
        break;
      case 43: {
        return sym(TOK_INTERSECT);
      }
      case 77:
        break;
      case 39: {
        return sym(TOK_REGEXP);
      }
      case 78:
        break;
      case 25: {
        return sym(TOK_PREDICATION);
      }
      case 79:
        break;
      case 5: {
        return sym(TOK_NAT_NUMBER, Integer.valueOf(yytext()));
      }
      case 80:
        break;
      case 45: {
        return sym(TOK_DECISIONEDGE);
      }
      case 81:
        break;
      case 12: {
        return sym(TOK_CONCAT);
      }
      case 82:
        break;
      case 24: {
        return sym(TOK_QUOTED_STRING, yytext());
      }
      case 83:
        break;
      case 22: {
        return sym(TOK_LESS_OR_EQ);
      }
      case 84:
        break;
      case 7: {
        return sym(TOK_EXIT);
      }
      case 85:
        break;
      case 8: {
        return sym(TOK_L_BRACE);
      }
      case 86:
        break;
      case 17: {
        return sym(TOK_IDENTITY);
      }
      case 87:
        break;
      case 11: {
        return sym(TOK_LESS);
      }
      case 88:
        break;
      case 41: {
        return sym(TOK_PASSING);
      }
      case 89:
        break;
      case 29: {
        return sym(TOK_EXPR);
      }
      case 90:
        break;
      case 49: {
        return sym(TOK_ENCLOSING_SCOPES);
      }
      case 91:
        break;
      case 6: {
        return sym(TOK_ENTRY);
      }
      case 92:
        break;
      case 46: {
        return sym(TOK_CONDITIONEDGE);
      }
      case 93:
        break;
      case 34: {
        return sym(TOK_UNION);
      }
      case 94:
        break;
      case 2: {
        return sym(TOK_R_PARENTHESIS);
      }
      case 95:
        break;
      case 10: {
        return sym(TOK_GREATER);
      }
      case 96:
        break;
      case 3: {
        return sym(TOK_COMMA);
      }
      case 97:
        break;
      case 15: {
        return sym(TOK_QUOTE);
      }
      case 98:
        break;
      default:
        if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
          zzAtEOF = true;
          zzDoEOF();
          {
            return new java_cup.runtime.Symbol(org.sosy_lab.cpachecker.core.algorithm.tiger.fql.parser.FQLSym.EOF);
          }
        }
        else {
          zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
