package vn.cmcglobal.trial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BreadcrumbItem {
    String url;
    String label;
}
