parser grammar EmuParserRules;
options {backtrack=true; output=AST;}

tokens {
	MUTATION;
	ROLE;
	CARDINALITY;
	DOMAIN;
	NO;
	OPTIONAL;
	ACTIVE;
	ACTION;
}

@members {

	public void setTokenType(ParserRuleReturnScope tree, int type) {
		((CommonTree) tree.getTree()).getToken().setType(type);
	}
}

mutation
	@after {
		$tree.getExtraTokens().add($mu);
	}
	:
	mu='mutate'^ NAME role (','! role)* action
	{$mu.setType(MUTATION);}
	;

role
	: no? NAME (','! NAME)* n=':'^ t=typeName {setTokenType(t, TYPE);} cardinality? (domain | guard | optional | active)*
	{$n.setType(ROLE);}
	;


no : n='no' {$n.setType(NO);};

cardinality
	@after {
		$tree.getExtraTokens().add($cb);
	}
	: c='['^ bound ('..'! bound)? cb=']'!
	{$c.setType(CARDINALITY);}
	;

bound
	: INT | '*'
	;

domain :
	(c='in'^|c='from'^) expressionOrStatementBlock
	{$c.setType(DOMAIN);}
	;

optional :
	c='optional'^ expressionOrStatementBlock
	{$c.setType(OPTIONAL);}
	;

active :
	c='active'^ expressionOrStatementBlock
	{$c.setType(ACTIVE);}
	;

action
	:
	(a='byAdd'^|a='byDelete'^|a='byReplace'^) expressionOrStatementBlock
	{$a.setType(ACTION);}
	;