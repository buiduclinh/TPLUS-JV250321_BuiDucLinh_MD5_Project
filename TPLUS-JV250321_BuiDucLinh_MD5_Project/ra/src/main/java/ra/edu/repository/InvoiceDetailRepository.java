package ra.edu.repository;

import ra.edu.model.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail,Integer> {
    List<InvoiceDetail> findAllByInvoice_Id(Integer invoiceId);
}
