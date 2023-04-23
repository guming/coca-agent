package org.coca.agent.core.plugin;

import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.coca.agent.core.plugin.match.ClassMatch;
import org.coca.agent.core.plugin.match.IndirectMatch;
import org.coca.agent.core.plugin.match.NameMatch;

import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.not;

public class PluginFinder {
    private final Map<String, LinkedList<AbstractClassEnhancePluginDefine>> nameMatchDefine = new HashMap<>();
    private final List<AbstractClassEnhancePluginDefine> signatureMatchDefine = new ArrayList<>();
    /*
     just for jvm
     */
    private final List<AbstractClassEnhancePluginDefine> boostrapClassMatchDefine = new ArrayList<>();

    public PluginFinder(List<AbstractClassEnhancePluginDefine> defines) {
        for (AbstractClassEnhancePluginDefine define : defines) {
            ClassMatch classMatch = define.enhanceClass();
            if(classMatch == null){
                continue;
            }
            if (classMatch instanceof NameMatch) {
                NameMatch nameMatch = (NameMatch) classMatch;
                LinkedList<AbstractClassEnhancePluginDefine> pluginDefines = nameMatchDefine.get(nameMatch.getClassName());
                if (pluginDefines == null) {
                    pluginDefines = new LinkedList<AbstractClassEnhancePluginDefine>();
                    nameMatchDefine.put(nameMatch.getClassName(), pluginDefines);
                }
                pluginDefines.add(define);
            } else {
                signatureMatchDefine.add(define);
            }
            if(define.isBootstrapInstrumentation()){
                boostrapClassMatchDefine.add(define);
            }
        }
    }

    public List<AbstractClassEnhancePluginDefine> find(TypeDescription typeDescription) {
        List<AbstractClassEnhancePluginDefine> matchDefines = new LinkedList<>();
        String name = typeDescription.getName();
        if (nameMatchDefine.containsKey(name)) {
            matchDefines.addAll(nameMatchDefine.get(name));
        }
        for(AbstractClassEnhancePluginDefine define: signatureMatchDefine){
            IndirectMatch match = (IndirectMatch) define;
            if(match.isMatch(typeDescription)){
                matchDefines.add(define);
            }
        }
        return matchDefines;
    }

    public ElementMatcher<? super TypeDescription> buildMatch(){
        ElementMatcher.Junction junction = new AbstractJunction<NamedElement>(){
            @Override
            public boolean matches(NamedElement target) {
                return nameMatchDefine.containsKey(target.getActualName());
            }
        };
        junction.and(not(isInterface()));
        for (AbstractClassEnhancePluginDefine define : signatureMatchDefine) {
            ClassMatch classMatch = define.enhanceClass();
            if (classMatch instanceof IndirectMatch) {
                junction = junction.or(((IndirectMatch) classMatch).buildJunction());
            }
        }
        return junction;
    }
    public  List<AbstractClassEnhancePluginDefine> getBoostrapClassMatchDefine() {
        return boostrapClassMatchDefine;
    }

}

