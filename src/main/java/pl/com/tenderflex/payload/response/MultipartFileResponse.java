package pl.com.tenderflex.payload.response;

import java.net.URL;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MultipartFileResponse {

    private final URL fileUrl;
    
}