package com.routdoo.dailyroutine.common.mysql;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.type.StandardBasicTypes;

import static org.apache.poi.ss.formula.eval.FunctionEval.registerFunction;
import static org.hibernate.type.StandardBasicTypes.DOUBLE;

/**
 * packageName    : com.routdoo.dailyroutine.common.mysql
 * fileName       : MySQL8DialectCustom
 * author         : rhkdg
 * date           : 2024-04-12
 * description    : full text search in boolean mode
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-12        rhkdg       최초 생성
 */
public class CustomFunctionContributor implements FunctionContributor {

//    private static final String FUNCTION_NAME = "match_against";
//    private static final String FUNCTION_PATTERN = "match (?) against (?2 in boolean mode)";
    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {

        functionContributions.getFunctionRegistry()
                .registerPattern("match_against_boolean","match (?1) against (?2 in boolean mode)",
                        functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve(DOUBLE));

        functionContributions.getFunctionRegistry()
                .registerPattern("match_daily_routine_community","match (?1,?2) against (?3 in boolean mode)",
                        functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve(DOUBLE));

    }
}
