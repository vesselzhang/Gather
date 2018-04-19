package com.vessel.gather.app.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author vesselzhang
 * @date 2017/12/5
 */

public class NotePadResponse implements Serializable {

    private List<NotesBean> notes;

    public List<NotesBean> getNotes() {
        return notes;
    }

    public void setNotes(List<NotesBean> notes) {
        this.notes = notes;
    }

    public static class NotesBean {
        /**
         * notepadId : dsjsjlkjkljdskls
         * title : 标题
         * time : 2018/03/03
         * content : 今日到xxx修理下水道....
         */

        private String notepadId;
        private String title;
        private String time;
        private String content;

        public String getNotepadId() {
            return notepadId;
        }

        public void setNotepadId(String notepadId) {
            this.notepadId = notepadId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
