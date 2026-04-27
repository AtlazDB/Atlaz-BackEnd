package com.example.AtlazDB.service;

import com.example.AtlazDB.model.Fuel;
import com.example.AtlazDB.model.OrderService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CreateCsv {

    private String escape(Object value) {
        if (value == null) return "";
        String str = value.toString();
        if (str.contains(",") || str.contains("\"") || str.contains("\n")) {
            return "\"" + str.replace("\"", "\"\"") + "\"";
        }
        return str;
    }

    public byte[] createCSV(List<OrderService> orders, List<Fuel> fuels) {
        StringBuilder csv = new StringBuilder();
        csv.append("viatura,tipo_servico,justification,requisition,destino,km_saida,km_chegada,data_saida,data_retorno,liters,valor_total,nota_fiscal\n");

        for (OrderService os : orders) {
            // Searches for fueling related to the OS
            Fuel ab = fuels.stream()
                    .filter(a -> a.getOrderService() != null && a.getOrderService().getId().equals(os.getId()))
                    .findFirst()
                    .orElse(null);

            csv.append(escape(os.getVehicle().getPrefix())).append(",");
            csv.append(escape(os.getTypeService())).append(",");
            csv.append(escape(os.getJustification())).append(",");
            csv.append(escape(os.getRequisition())).append(",");
            csv.append(escape(os.getLocalDestiny())).append(",");
            csv.append(escape(os.getLeaveKm())).append(",");
            csv.append(escape(os.getArriveKm())).append(",");
            csv.append(escape(os.getLeaveDate())).append(",");
            csv.append(escape(os.getReturnDate())).append(",");
            csv.append(ab != null ? escape(ab.getLiters()) : "").append(",");
            csv.append(ab != null ? escape(ab.getTotalValue()) : "").append(",");
            csv.append(ab != null ? escape(ab.getNumberReceipt()) : "").append("\n");
        }

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }
}

