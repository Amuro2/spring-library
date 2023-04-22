package fr.amurotakahashi.cefimtestcda2.services;

import fr.amurotakahashi.cefimtestcda2.entities.Notice;
import fr.amurotakahashi.cefimtestcda2.repositories.NoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    public Notice getNoticeById(Integer id) {
        return noticeRepository.findById(id).orElse(null);
    }

//    public List<Notice> getNoticesByName(String name) {
//        return noticeRepository.findByName(name);
//    }

    public Notice postNotice(Notice notice) {
        if(notice == null) {
            throw new InvalidParameterException("Notice must not be null");
        }

        Optional<Notice> optionalNotice = noticeRepository.findById(notice.getId());

        if(optionalNotice.isEmpty()) {
            return noticeRepository.save(notice);
        } else {
            return optionalNotice.get();
        }
    }

//    public Notice patchNoticeName(Integer id, String name) {
//        Optional<Notice> optionalNotice = noticeRepository.findById(id);
//
//        if(optionalNotice.isPresent()) {
//            Notice notice = optionalNotice.get();
//
//            notice.setName(name);
//
//            return noticeRepository.save(notice);
//        } else {
//            throw new EntityNotFoundException();
//        }
//    }

    public Notice putNotice(Integer id, Notice notice) {
        if(notice == null) {
            throw new InvalidParameterException("Notice must not be null");
        }

        notice.setId(id);

        Optional<Notice> optionalNotice = noticeRepository.findById(id);

        if(optionalNotice.isPresent()) {
            return noticeRepository.save(notice);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Notice deleteNoticeById(Integer id) {
        Optional<Notice> optionalNotice = noticeRepository.findById(id);

        if(optionalNotice.isPresent()) {
            noticeRepository.deleteById(id);

            return optionalNotice.get();
        } else {
            throw new EntityNotFoundException();
        }
    }
}
