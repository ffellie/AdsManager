package app.components.search;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Search extends HorizontalLayout {
    private Grid grid;
    private SearchService service;
    private TextField searchField;
    private Button filterButton;
    private ComboBox<String> filterBox;
    private boolean filterBoxEnabled = true;
    public Search (Grid grid, SearchService service){
        this.grid = grid;
        this.service = service;
        filterButton = new Button();
        filterBox = new ComboBox<>();
        searchField = new TextField();
        add(searchField,filterBox,filterButton);
        configureSearchButton();
    }

    private void configureSearchButton (){
        filterButton.addClickListener(e -> {
            if (searchField.isEmpty())
                filterUsers("searchField.getValue()",filterBox.getValue());
            else if (filterBoxEnabled)
                filterUsers(searchField.getValue(),filterBox.getValue());
            else filterUsers(searchField.getValue(),null);

        });
        searchField.addValueChangeListener(e -> filterUsers(searchField.getValue(),"name"));
    }
    private void  filterUsers(String param, String filter){
        grid.getDataProvider().refreshAll();
        DataProvider dataProvider = DataProvider.fromCallbacks(
                query -> {
                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    List itemList;
                    Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                            .collect(Collectors.toMap(
                                    sort -> sort.getSorted(),
                                    sort -> sort.getDirection() == SortDirection.ASCENDING));

                    itemList = service
                            .find(offset, limit, param, filter, sortOrder);

                    return  itemList.stream();
                },
                query -> {
                    return service.count(param,filter);
                }
        );
        grid.setDataProvider(dataProvider);

    }

    public void setFilterBoxEnabled(boolean enabled){
        filterBox.setVisible(enabled);
        filterBoxEnabled = enabled;
    }
}
