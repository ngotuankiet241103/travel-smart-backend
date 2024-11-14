package com.travelsmart.event.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationCommand {
   private  String chanel;
   private  String recipient;
   private  String templateCode;
   private  Map<String,Object> params;
   private  String nextEvent;


}
