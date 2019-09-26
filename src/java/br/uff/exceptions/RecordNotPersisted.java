/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.exceptions;

/**
 *
 * @author felipe
 */
public class RecordNotPersisted extends Exception {
    public RecordNotPersisted() {
        super("This record is not persisted on the database");
    }
}
