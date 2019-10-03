/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sql;

/**
 *
 * @author felipe
 */
public abstract class SqlExecutioner {
    public abstract <T> T run();
}
