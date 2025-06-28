    package main;

    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */

    /**
     *
     * @author 2830482311004
     */
    public class categoria {
        private int id;
        private String nome;
        private String descricao;

        public int getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public void SetId(int id) {
            this.id = id;
        }

        public void SetNome(String nome) {
            this.nome = nome;
        }

        public void SetDescricao(String descricao) {
            this.descricao = descricao;
        }

        public String toString() {
            return this.nome;
        } 

    }
