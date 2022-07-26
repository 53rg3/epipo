package io.github.ss3rg3.pong.utils;

import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;

/**
 * Author @espresso stackoverflow.
 * Sample use:
 * Model.Person reqObj = ProtoUtil.toProto(reqJson, Model.Person.getDefaultInstance());
 * Model.Person res = personSvc.update(reqObj);
 * final String resJson = ProtoUtil.toJson(res);
 **/
public class PROTO {

    public static <T extends Message> String toJson(T obj) {
        try {
            return JsonFormat.printer().print(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends MessageOrBuilder> T toProto(String protoJsonStr, T defaultMessage) {
        try {
            Message.Builder builder = defaultMessage.getDefaultInstanceForType().toBuilder();
            JsonFormat.parser().ignoringUnknownFields().merge(protoJsonStr, builder);
            return (T) builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
