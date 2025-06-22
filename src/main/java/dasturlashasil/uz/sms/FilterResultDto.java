package dasturlashasil.uz.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//public class FilterResultDTO {
//    public FilterResultDTO(List<Object[]> profileList, Long totalCount) {
//    }
//}
@Getter
@Setter
@AllArgsConstructor
public class FilterResultDto<E> {
    private List<E> content;
    private Long total;

}

