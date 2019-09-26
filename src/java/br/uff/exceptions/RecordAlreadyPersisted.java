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
public class RecordAlreadyPersisted extends Exception {
    public RecordAlreadyPersisted() {
        super("This record is already saved on the database");
    }
}
