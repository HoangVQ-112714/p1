package vn.cmcglobal.trial.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import vn.cmcglobal.trial.model.BreadcrumbItem;

import java.lang.reflect.Array;
import java.util.*;

@Getter
@Service
public class BreadcrumbService {
    private final Set<BreadcrumbItem> breadcrumbs = new LinkedHashSet<>();

    public void addBreadcrumb(Map<String, String> breadcrumbArr) {
        clearBreadcrumb();

        for (String i : breadcrumbArr.keySet()) {
            breadcrumbs.add(new BreadcrumbItem(i, breadcrumbArr.get(i)));
        }
    }

    public void clearBreadcrumb() {
        if (!breadcrumbs.isEmpty()) {
            breadcrumbs.clear();
        }
    }

}
