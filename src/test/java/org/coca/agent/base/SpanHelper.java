package org.coca.agent.base;

import org.coca.agent.core.context.SpanLogData;
import org.coca.agent.core.context.trace.Span;
import org.coca.agent.core.util.TagValuePair;

import java.util.Collections;
import java.util.List;

public class SpanHelper {
    public static int getParentSpanId(Span tracingSpan) {
        try {
            return FieldGetter.get2LevelParentFieldValue(tracingSpan, "parentSpanId");
        } catch (Exception e) {
            try {
                return FieldGetter.getParentFieldValue(tracingSpan, "parentSpanId");
            } catch (Exception e1) {

            }
        }

        return -9999;
    }

    public static List<SpanLogData> getLogs(Span tracingSpan) {
        try {
            List<SpanLogData> logs = FieldGetter.get2LevelParentFieldValue(tracingSpan, "logs");
            if (logs != null) {
                return logs;
            }
        } catch (Exception e) {
            try {
                List<SpanLogData> logs = FieldGetter.getParentFieldValue(tracingSpan, "logs");
                if (logs != null) {
                    return logs;
                }
            } catch (Exception e1) {

            }
        }

        return Collections.emptyList();
    }

    public static List<TagValuePair> getTags(Span tracingSpan) {
        try {
            List<TagValuePair> tags = FieldGetter.get2LevelParentFieldValue(tracingSpan, "tags");
            if (tags != null) {
                return tags;
            }
        } catch (Exception e) {
            try {
                List<TagValuePair> tags = FieldGetter.getParentFieldValue(tracingSpan, "tags");
                if (tags != null) {
                    return tags;
                }
            } catch (Exception e1) {

            }
        }

        return Collections.emptyList();
    }

//    public static SpanLayer getLayer(Span tracingSpan) {
//        try {
//            return FieldGetter.get2LevelParentFieldValue(tracingSpan, "layer");
//        } catch (Exception e) {
//            try {
//                return FieldGetter.getParentFieldValue(tracingSpan, "layer");
//            } catch (Exception e1) {
//
//            }
//        }
//
//        return null;
//    }

    public static String getComponentName(Span tracingSpan) {
        try {
            return FieldGetter.get2LevelParentFieldValue(tracingSpan, "componentName");
        } catch (Exception e) {
            try {
                return FieldGetter.getParentFieldValue(tracingSpan, "componentName");
            } catch (Exception e1) {

            }
        }

        return null;
    }

    public static int getComponentId(Span tracingSpan) {
        try {
            return FieldGetter.get2LevelParentFieldValue(tracingSpan, "componentId");
        } catch (Exception e) {
            try {
                return FieldGetter.getParentFieldValue(tracingSpan, "componentId");
            } catch (Exception e1) {

            }
        }

        return -1;
    }

    public static boolean getErrorOccurred(Span tracingSpan) {
        try {
            return FieldGetter.get2LevelParentFieldValue(tracingSpan, "errorOccurred");
        } catch (Exception e) {
            try {
                return FieldGetter.getParentFieldValue(tracingSpan, "errorOccurred");
            } catch (Exception e1) {

            }
        }

        return false;
    }
}
