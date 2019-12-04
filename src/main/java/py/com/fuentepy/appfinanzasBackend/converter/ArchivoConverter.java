package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;

import java.util.ArrayList;
import java.util.List;

@Component("archivoConverter")
public class ArchivoConverter {

    public static ArchivoModel entityToArchivoModel(Archivo entity) {
        ArchivoModel model = new ArchivoModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setContentType(entity.getContentType());
        return model;
    }

    public static List<ArchivoModel> listEntitytoListModel(List<Archivo> listEntity) {
        List<ArchivoModel> listModel = new ArrayList<>();
        for (Archivo entity : listEntity) {
            listModel.add(entityToArchivoModel(entity));
        }
        return listModel;
    }

}
