package dasturlashasil.uz.mapperL;

import java.time.LocalDateTime;

public interface ArticleShortInfo {

    String getId();
    String getTitle();
//    (uuid),title, description,id,url),published_date
    String getDescription();
//    description,id,url),published_date
    String getImageId();
    LocalDateTime getPublishedDate();//(id,url),published_date

}
