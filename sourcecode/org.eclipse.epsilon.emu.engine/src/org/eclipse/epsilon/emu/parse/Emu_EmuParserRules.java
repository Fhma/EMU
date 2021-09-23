// $ANTLR 3.1b1 EmuParserRules.g 2021-06-15 15:21:20

/*******************************************************************************
 * Copyright (c) 2018 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Faisal Alhwikem
 * -----------------------------------------------------------------------------
 * ANTLR 3 License
 * [The "BSD licence"]
 * Copyright (c) 2005-2008 Terence Parr
 * All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package org.eclipse.epsilon.emu.parse;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class Emu_EmuParserRules extends org.eclipse.epsilon.common.parse.EpsilonParser {
    public static final int T__144=144;
    public static final int T__143=143;
    public static final int T__146=146;
    public static final int MODELDECLARATIONPARAMETER=72;
    public static final int T__145=145;
    public static final int BREAKALL=39;
    public static final int T__140=140;
    public static final int T__142=142;
    public static final int VAR=48;
    public static final int MODELDECLARATIONPARAMETERS=71;
    public static final int T__141=141;
    public static final int THROW=53;
    public static final int PARAMLIST=25;
    public static final int EXPRLIST=54;
    public static final int EXPRRANGE=55;
    public static final int BREAK=38;
    public static final int ELSE=32;
    public static final int T__137=137;
    public static final int T__136=136;
    public static final int FORMAL=24;
    public static final int IF=31;
    public static final int MultiplicativeExpression=57;
    public static final int TYPE=64;
    public static final int T__139=139;
    public static final int T__138=138;
    public static final int T__133=133;
    public static final int T__132=132;
    public static final int T__135=135;
    public static final int T__134=134;
    public static final int EMUMODULE=89;
    public static final int T__131=131;
    public static final int NewExpression=47;
    public static final int T__130=130;
    public static final int CASE=35;
    public static final int Letter=16;
    public static final int LINE_COMMENT=22;
    public static final int T__129=129;
    public static final int T__126=126;
    public static final int JavaIDDigit=18;
    public static final int T__125=125;
    public static final int MAP=74;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int T__166=166;
    public static final int T__165=165;
    public static final int T__168=168;
    public static final int DOMAIN=84;
    public static final int T__167=167;
    public static final int T__162=162;
    public static final int T__161=161;
    public static final int T__164=164;
    public static final int MODELDECLARATION=67;
    public static final int T__163=163;
    public static final int EXPRESSIONINBRACKETS=59;
    public static final int T__160=160;
    public static final int TRANSACTION=41;
    public static final int FLOAT_TYPE_SUFFIX=7;
    public static final int ITEMSELECTOR=73;
    public static final int COMMENT=21;
    public static final int ModelElementType=45;
    public static final int ROLE=82;
    public static final int IMPORT=66;
    public static final int DELETE=52;
    public static final int ARROW=11;
    public static final int T__159=159;
    public static final int T__158=158;
    public static final int T__155=155;
    public static final int SPECIAL_ASSIGNMENT=27;
    public static final int T__154=154;
    public static final int T__157=157;
    public static final int T__156=156;
    public static final int T__151=151;
    public static final int T__150=150;
    public static final int T__153=153;
    public static final int T__152=152;
    public static final int Annotation=23;
    public static final int CONTINUE=40;
    public static final int ENUMERATION_VALUE=65;
    public static final int OPERATOR=58;
    public static final int EXPONENT=6;
    public static final int STRING=14;
    public static final int T__148=148;
    public static final int T__147=147;
    public static final int T__149=149;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int NAMESPACE=68;
    public static final int T__92=92;
    public static final int COLLECTION=42;
    public static final int NEW=49;
    public static final int EXTENDS=79;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int PRE=77;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int POST=78;
    public static final int T__90=90;
    public static final int ALIAS=69;
    public static final int DRIVER=70;
    public static final int OPTIONAL=86;
    public static final int KEYVAL=75;
    public static final int POINT_POINT=10;
    public static final int GUARD=80;
    public static final int MUTATION=81;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int HELPERMETHOD=28;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int StatementBlock=29;
    public static final int T__98=98;
    public static final int ABORT=43;
    public static final int StrangeNameLiteral=15;
    public static final int FOR=30;
    public static final int BLOCK=62;
    public static final int T__171=171;
    public static final int T__170=170;
    public static final int PARAMETERS=46;
    public static final int SpecialNameChar=17;
    public static final int BOOLEAN=12;
    public static final int NAME=19;
    public static final int SWITCH=34;
    public static final int T__169=169;
    public static final int FeatureCall=60;
    public static final int T__122=122;
    public static final int T__121=121;
    public static final int NO=85;
    public static final int T__124=124;
    public static final int FLOAT=4;
    public static final int T__123=123;
    public static final int ACTIVE=87;
    public static final int T__120=120;
    public static final int NativeType=56;
    public static final int INT=8;
    public static final int ANNOTATIONBLOCK=50;
    public static final int RETURN=37;
    public static final int KEYVALLIST=76;
    public static final int FEATURECALL=63;
    public static final int CollectionType=44;
    public static final int T__119=119;
    public static final int ASSIGNMENT=26;
    public static final int T__118=118;
    public static final int CARDINALITY=83;
    public static final int T__115=115;
    public static final int WS=20;
    public static final int EOF=-1;
    public static final int T__114=114;
    public static final int T__117=117;
    public static final int T__116=116;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int EscapeSequence=13;
    public static final int EOLMODULE=61;
    public static final int DIGIT=5;
    public static final int EXECUTABLEANNOTATION=51;
    public static final int ACTION=88;
    public static final int T__108=108;
    public static final int T__107=107;
    public static final int WHILE=33;
    public static final int T__109=109;
    public static final int T__104=104;
    public static final int POINT=9;
    public static final int T__103=103;
    public static final int T__106=106;
    public static final int DEFAULT=36;
    public static final int T__105=105;

    // delegates
    // delegators
    public EmuParser gEmu;


        public Emu_EmuParserRules(TokenStream input, EmuParser gEmu) {
            this(input, new RecognizerSharedState(), gEmu);
        }
        public Emu_EmuParserRules(TokenStream input, RecognizerSharedState state, EmuParser gEmu) {
            super(input, state);
            this.gEmu = gEmu;
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EmuParser.tokenNames; }
    public String getGrammarFileName() { return "EmuParserRules.g"; }



    	public void setTokenType(ParserRuleReturnScope tree, int type) {
    		((CommonTree) tree.getTree()).getToken().setType(type);
    	}


    public static class mutation_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start mutation
    // EmuParserRules.g:22:1: mutation : mu= 'mutate' NAME role ( ',' role )* action ;
    public final Emu_EmuParserRules.mutation_return mutation() throws RecognitionException {
        Emu_EmuParserRules.mutation_return retval = new Emu_EmuParserRules.mutation_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token mu=null;
        Token NAME1=null;
        Token char_literal3=null;
        Emu_EmuParserRules.role_return role2 = null;

        Emu_EmuParserRules.role_return role4 = null;

        Emu_EmuParserRules.action_return action5 = null;


        org.eclipse.epsilon.common.parse.AST mu_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME1_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal3_tree=null;

        try {
            // EmuParserRules.g:26:2: (mu= 'mutate' NAME role ( ',' role )* action )
            // EmuParserRules.g:27:2: mu= 'mutate' NAME role ( ',' role )* action
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            mu=(Token)match(input,164,FOLLOW_164_in_mutation77); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            mu_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(mu);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(mu_tree, root_0);
            }
            NAME1=(Token)match(input,NAME,FOLLOW_NAME_in_mutation80); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME1_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME1);
            adaptor.addChild(root_0, NAME1_tree);
            }
            pushFollow(FOLLOW_role_in_mutation82);
            role2=role();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, role2.getTree());
            // EmuParserRules.g:27:25: ( ',' role )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==93) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // EmuParserRules.g:27:26: ',' role
            	    {
            	    char_literal3=(Token)match(input,93,FOLLOW_93_in_mutation85); if (state.failed) return retval;
            	    pushFollow(FOLLOW_role_in_mutation88);
            	    role4=role();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, role4.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            pushFollow(FOLLOW_action_in_mutation92);
            action5=action();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, action5.getTree());
            if ( state.backtracking==0 ) {
              mu.setType(MUTATION);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(mu);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end mutation

    public static class role_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start role
    // EmuParserRules.g:31:1: role : ( no )? NAME ( ',' NAME )* n= ':' t= typeName ( cardinality )? ( domain | guard | optional | active )* ;
    public final Emu_EmuParserRules.role_return role() throws RecognitionException {
        Emu_EmuParserRules.role_return retval = new Emu_EmuParserRules.role_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token n=null;
        Token NAME7=null;
        Token char_literal8=null;
        Token NAME9=null;
        Emu_EolParserRules.typeName_return t = null;

        Emu_EmuParserRules.no_return no6 = null;

        Emu_EmuParserRules.cardinality_return cardinality10 = null;

        Emu_EmuParserRules.domain_return domain11 = null;

        Emu_ErlParserRules.guard_return guard12 = null;

        Emu_EmuParserRules.optional_return optional13 = null;

        Emu_EmuParserRules.active_return active14 = null;


        org.eclipse.epsilon.common.parse.AST n_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME7_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal8_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME9_tree=null;

        try {
            // EmuParserRules.g:32:2: ( ( no )? NAME ( ',' NAME )* n= ':' t= typeName ( cardinality )? ( domain | guard | optional | active )* )
            // EmuParserRules.g:32:4: ( no )? NAME ( ',' NAME )* n= ':' t= typeName ( cardinality )? ( domain | guard | optional | active )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EmuParserRules.g:32:4: ( no )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==165) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // EmuParserRules.g:0:0: no
                    {
                    pushFollow(FOLLOW_no_in_role106);
                    no6=no();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, no6.getTree());

                    }
                    break;

            }

            NAME7=(Token)match(input,NAME,FOLLOW_NAME_in_role109); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME7_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME7);
            adaptor.addChild(root_0, NAME7_tree);
            }
            // EmuParserRules.g:32:13: ( ',' NAME )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==93) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // EmuParserRules.g:32:14: ',' NAME
            	    {
            	    char_literal8=(Token)match(input,93,FOLLOW_93_in_role112); if (state.failed) return retval;
            	    NAME9=(Token)match(input,NAME,FOLLOW_NAME_in_role115); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    NAME9_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME9);
            	    adaptor.addChild(root_0, NAME9_tree);
            	    }

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            n=(Token)match(input,102,FOLLOW_102_in_role121); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            n_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(n);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(n_tree, root_0);
            }
            pushFollow(FOLLOW_typeName_in_role126);
            t=gEmu.typeName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if ( state.backtracking==0 ) {
              setTokenType(t, TYPE);
            }
            // EmuParserRules.g:32:69: ( cardinality )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==154) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // EmuParserRules.g:0:0: cardinality
                    {
                    pushFollow(FOLLOW_cardinality_in_role130);
                    cardinality10=cardinality();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cardinality10.getTree());

                    }
                    break;

            }

            // EmuParserRules.g:32:82: ( domain | guard | optional | active )*
            loop5:
            do {
                int alt5=5;
                switch ( input.LA(1) ) {
                case 119:
                case 166:
                    {
                    alt5=1;
                    }
                    break;
                case 162:
                    {
                    alt5=2;
                    }
                    break;
                case 167:
                    {
                    alt5=3;
                    }
                    break;
                case 168:
                    {
                    alt5=4;
                    }
                    break;

                }

                switch (alt5) {
            	case 1 :
            	    // EmuParserRules.g:32:83: domain
            	    {
            	    pushFollow(FOLLOW_domain_in_role134);
            	    domain11=domain();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, domain11.getTree());

            	    }
            	    break;
            	case 2 :
            	    // EmuParserRules.g:32:92: guard
            	    {
            	    pushFollow(FOLLOW_guard_in_role138);
            	    guard12=gEmu.guard();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, guard12.getTree());

            	    }
            	    break;
            	case 3 :
            	    // EmuParserRules.g:32:100: optional
            	    {
            	    pushFollow(FOLLOW_optional_in_role142);
            	    optional13=optional();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, optional13.getTree());

            	    }
            	    break;
            	case 4 :
            	    // EmuParserRules.g:32:111: active
            	    {
            	    pushFollow(FOLLOW_active_in_role146);
            	    active14=active();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, active14.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            if ( state.backtracking==0 ) {
              n.setType(ROLE);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end role

    public static class no_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start no
    // EmuParserRules.g:37:1: no : n= 'no' ;
    public final Emu_EmuParserRules.no_return no() throws RecognitionException {
        Emu_EmuParserRules.no_return retval = new Emu_EmuParserRules.no_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token n=null;

        org.eclipse.epsilon.common.parse.AST n_tree=null;

        try {
            // EmuParserRules.g:37:4: (n= 'no' )
            // EmuParserRules.g:37:6: n= 'no'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            n=(Token)match(input,165,FOLLOW_165_in_no164); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            n_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(n);
            adaptor.addChild(root_0, n_tree);
            }
            if ( state.backtracking==0 ) {
              n.setType(NO);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end no

    public static class cardinality_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cardinality
    // EmuParserRules.g:39:1: cardinality : c= '[' bound ( '..' bound )? cb= ']' ;
    public final Emu_EmuParserRules.cardinality_return cardinality() throws RecognitionException {
        Emu_EmuParserRules.cardinality_return retval = new Emu_EmuParserRules.cardinality_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Token cb=null;
        Token string_literal16=null;
        Emu_EmuParserRules.bound_return bound15 = null;

        Emu_EmuParserRules.bound_return bound17 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal16_tree=null;

        try {
            // EmuParserRules.g:43:2: (c= '[' bound ( '..' bound )? cb= ']' )
            // EmuParserRules.g:43:4: c= '[' bound ( '..' bound )? cb= ']'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,154,FOLLOW_154_in_cardinality183); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_bound_in_cardinality186);
            bound15=bound();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, bound15.getTree());
            // EmuParserRules.g:43:17: ( '..' bound )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==POINT_POINT) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // EmuParserRules.g:43:18: '..' bound
                    {
                    string_literal16=(Token)match(input,POINT_POINT,FOLLOW_POINT_POINT_in_cardinality189); if (state.failed) return retval;
                    pushFollow(FOLLOW_bound_in_cardinality192);
                    bound17=bound();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, bound17.getTree());

                    }
                    break;

            }

            cb=(Token)match(input,155,FOLLOW_155_in_cardinality198); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              c.setType(CARDINALITY);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(cb);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end cardinality

    public static class bound_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start bound
    // EmuParserRules.g:47:1: bound : ( INT | '*' );
    public final Emu_EmuParserRules.bound_return bound() throws RecognitionException {
        Emu_EmuParserRules.bound_return retval = new Emu_EmuParserRules.bound_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token set18=null;

        org.eclipse.epsilon.common.parse.AST set18_tree=null;

        try {
            // EmuParserRules.g:48:2: ( INT | '*' )
            // EmuParserRules.g:
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            set18=(Token)input.LT(1);
            if ( input.LA(1)==INT||input.LA(1)==150 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (org.eclipse.epsilon.common.parse.AST)adaptor.create(set18));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end bound

    public static class domain_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start domain
    // EmuParserRules.g:51:1: domain : (c= 'in' | c= 'from' ) expressionOrStatementBlock ;
    public final Emu_EmuParserRules.domain_return domain() throws RecognitionException {
        Emu_EmuParserRules.domain_return retval = new Emu_EmuParserRules.domain_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Emu_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock19 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EmuParserRules.g:51:8: ( (c= 'in' | c= 'from' ) expressionOrStatementBlock )
            // EmuParserRules.g:52:2: (c= 'in' | c= 'from' ) expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EmuParserRules.g:52:2: (c= 'in' | c= 'from' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==119) ) {
                alt7=1;
            }
            else if ( (LA7_0==166) ) {
                alt7=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // EmuParserRules.g:52:3: c= 'in'
                    {
                    c=(Token)match(input,119,FOLLOW_119_in_domain231); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
                    }

                    }
                    break;
                case 2 :
                    // EmuParserRules.g:52:11: c= 'from'
                    {
                    c=(Token)match(input,166,FOLLOW_166_in_domain236); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_expressionOrStatementBlock_in_domain240);
            expressionOrStatementBlock19=gEmu.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock19.getTree());
            if ( state.backtracking==0 ) {
              c.setType(DOMAIN);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end domain

    public static class optional_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start optional
    // EmuParserRules.g:56:1: optional : c= 'optional' expressionOrStatementBlock ;
    public final Emu_EmuParserRules.optional_return optional() throws RecognitionException {
        Emu_EmuParserRules.optional_return retval = new Emu_EmuParserRules.optional_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Emu_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock20 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EmuParserRules.g:56:10: (c= 'optional' expressionOrStatementBlock )
            // EmuParserRules.g:57:2: c= 'optional' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,167,FOLLOW_167_in_optional256); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_optional259);
            expressionOrStatementBlock20=gEmu.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock20.getTree());
            if ( state.backtracking==0 ) {
              c.setType(OPTIONAL);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end optional

    public static class active_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start active
    // EmuParserRules.g:61:1: active : c= 'active' expressionOrStatementBlock ;
    public final Emu_EmuParserRules.active_return active() throws RecognitionException {
        Emu_EmuParserRules.active_return retval = new Emu_EmuParserRules.active_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Emu_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock21 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EmuParserRules.g:61:8: (c= 'active' expressionOrStatementBlock )
            // EmuParserRules.g:62:2: c= 'active' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,168,FOLLOW_168_in_active275); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_active278);
            expressionOrStatementBlock21=gEmu.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock21.getTree());
            if ( state.backtracking==0 ) {
              c.setType(ACTIVE);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end active

    public static class action_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start action
    // EmuParserRules.g:66:1: action : (a= 'byAdd' | a= 'byDelete' | a= 'byReplace' ) expressionOrStatementBlock ;
    public final Emu_EmuParserRules.action_return action() throws RecognitionException {
        Emu_EmuParserRules.action_return retval = new Emu_EmuParserRules.action_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token a=null;
        Emu_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock22 = null;


        org.eclipse.epsilon.common.parse.AST a_tree=null;

        try {
            // EmuParserRules.g:67:2: ( (a= 'byAdd' | a= 'byDelete' | a= 'byReplace' ) expressionOrStatementBlock )
            // EmuParserRules.g:68:2: (a= 'byAdd' | a= 'byDelete' | a= 'byReplace' ) expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EmuParserRules.g:68:2: (a= 'byAdd' | a= 'byDelete' | a= 'byReplace' )
            int alt8=3;
            switch ( input.LA(1) ) {
            case 169:
                {
                alt8=1;
                }
                break;
            case 170:
                {
                alt8=2;
                }
                break;
            case 171:
                {
                alt8=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // EmuParserRules.g:68:3: a= 'byAdd'
                    {
                    a=(Token)match(input,169,FOLLOW_169_in_action296); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    a_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(a);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(a_tree, root_0);
                    }

                    }
                    break;
                case 2 :
                    // EmuParserRules.g:68:14: a= 'byDelete'
                    {
                    a=(Token)match(input,170,FOLLOW_170_in_action301); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    a_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(a);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(a_tree, root_0);
                    }

                    }
                    break;
                case 3 :
                    // EmuParserRules.g:68:28: a= 'byReplace'
                    {
                    a=(Token)match(input,171,FOLLOW_171_in_action306); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    a_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(a);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(a_tree, root_0);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_expressionOrStatementBlock_in_action310);
            expressionOrStatementBlock22=gEmu.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock22.getTree());
            if ( state.backtracking==0 ) {
              a.setType(ACTION);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end action

    // Delegated rules


 

    public static final BitSet FOLLOW_164_in_mutation77 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_mutation80 = new BitSet(new long[]{0x0000000000080000L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_role_in_mutation82 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L,0x00000E0000000000L});
    public static final BitSet FOLLOW_93_in_mutation85 = new BitSet(new long[]{0x0000000000080000L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_role_in_mutation88 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L,0x00000E0000000000L});
    public static final BitSet FOLLOW_action_in_mutation92 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_no_in_role106 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_role109 = new BitSet(new long[]{0x0000000000000000L,0x0000004020000000L});
    public static final BitSet FOLLOW_93_in_role112 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_role115 = new BitSet(new long[]{0x0000000000000000L,0x0000004020000000L});
    public static final BitSet FOLLOW_102_in_role121 = new BitSet(new long[]{0x0000000000080000L,0x000FF00000000000L});
    public static final BitSet FOLLOW_typeName_in_role126 = new BitSet(new long[]{0x0000000000000002L,0x0080000000000000L,0x000001C404000000L});
    public static final BitSet FOLLOW_cardinality_in_role130 = new BitSet(new long[]{0x0000000000000002L,0x0080000000000000L,0x000001C400000000L});
    public static final BitSet FOLLOW_domain_in_role134 = new BitSet(new long[]{0x0000000000000002L,0x0080000000000000L,0x000001C400000000L});
    public static final BitSet FOLLOW_guard_in_role138 = new BitSet(new long[]{0x0000000000000002L,0x0080000000000000L,0x000001C400000000L});
    public static final BitSet FOLLOW_optional_in_role142 = new BitSet(new long[]{0x0000000000000002L,0x0080000000000000L,0x000001C400000000L});
    public static final BitSet FOLLOW_active_in_role146 = new BitSet(new long[]{0x0000000000000002L,0x0080000000000000L,0x000001C400000000L});
    public static final BitSet FOLLOW_165_in_no164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_154_in_cardinality183 = new BitSet(new long[]{0x0000000000000100L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_bound_in_cardinality186 = new BitSet(new long[]{0x0000000000000400L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_POINT_POINT_in_cardinality189 = new BitSet(new long[]{0x0000000000000100L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_bound_in_cardinality192 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_155_in_cardinality198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_bound0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_119_in_domain231 = new BitSet(new long[]{0x0000000000000000L,0x0000004080000000L});
    public static final BitSet FOLLOW_166_in_domain236 = new BitSet(new long[]{0x0000000000000000L,0x0000004080000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_domain240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_167_in_optional256 = new BitSet(new long[]{0x0000000000000000L,0x0000004080000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_optional259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_168_in_active275 = new BitSet(new long[]{0x0000000000000000L,0x0000004080000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_active278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_169_in_action296 = new BitSet(new long[]{0x0000000000000000L,0x0000004080000000L});
    public static final BitSet FOLLOW_170_in_action301 = new BitSet(new long[]{0x0000000000000000L,0x0000004080000000L});
    public static final BitSet FOLLOW_171_in_action306 = new BitSet(new long[]{0x0000000000000000L,0x0000004080000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_action310 = new BitSet(new long[]{0x0000000000000002L});

}
