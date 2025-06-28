package banco;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author 2830482311004
 */
public class Conexao {
    
    public Connection getConexao(){
        try{
            Connection coon = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/loja?useTimezone=true&serverTimezone=UTC",
                "root", "root");
            System.out.println("Conexão realizada com sucesso!");
            return coon;
        }
        catch(Exception e){
            System.out.println("Erro ao conectar no BD: " + e.getMessage());
            return null;
        }
    }
    
    
       
}
    
