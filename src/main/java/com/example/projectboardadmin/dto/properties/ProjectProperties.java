package com.example.projectboardadmin.dto.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("project")
public class ProjectProperties {

    private Board board;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public static class Board {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}