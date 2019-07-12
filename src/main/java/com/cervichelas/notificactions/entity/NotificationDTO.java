package com.cervichelas.notificactions.entity;

public class NotificationDTO {
	
	   private long id;
	    private String title;
	    private String body;
	    private String topic;
	    private String url_imagen;

	    public NotificationDTO() {
	    }

	    public NotificationDTO(long id, String title, String body, String topic, String url_imagen) {
	        this.setId(id);
	        this.setTitle(title);
	        this.setBody(body);
	        this.setTopic(topic);
	        this.setUrl_imagen(url_imagen);
	    }

	    @Override
	    public String toString() {
	        return "NotificationDTO{" +
	                "id=" + getId() +
	                ", title='" + getTitle() + '\'' +
	                ", body='" + getBody() + '\'' +
	                ", url_imagen='" + getUrl_imagen() + '\'' +
	                ", topic='" + getTopic() + '\'' +
	                '}';
	    }

	    public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getBody() {
	        return body;
	    }

	    public void setBody(String body) {
	        this.body = body;
	    }

	    public String getTopic() {
	        return topic;
	    }

	    public void setTopic(String topic) {
	        this.topic = topic;
	    }

		public String getUrl_imagen() {
			return url_imagen;
		}

		public void setUrl_imagen(String url_imagen) {
			this.url_imagen = url_imagen;
		}
	    
	    

}
